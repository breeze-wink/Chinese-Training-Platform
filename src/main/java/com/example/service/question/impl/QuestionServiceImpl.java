package com.example.service.question.impl;

import com.example.mapper.question.QuestionMapper;
import com.example.model.question.Question;
import com.example.service.question.QuestionService;
import com.example.service.rabbitmq.RabbitMQProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionMapper questionMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public QuestionServiceImpl(QuestionMapper questionMapper,
                               RedisTemplate<String, Object> redisTemplate,
                               RabbitMQProducer rabbitMQProducer) {
        this.questionMapper = questionMapper;
        this.redisTemplate = redisTemplate;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    @Transactional
    public int createQuestion(Question question) {
        int result = questionMapper.insert(question);
        // 发送同步消息
        rabbitMQProducer.sendQuestionSyncMessage(question);
        return result;
    }

    @Override
    @Transactional
    public int deleteQuestion(Long id) throws JsonProcessingException {
        Question question = getQuestionById(id);
        int result = questionMapper.delete(id);
        // 发送同步消息
        if (question != null) {
            rabbitMQProducer.sendQuestionSyncMessage(question);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestionById(Long id) throws JsonProcessingException {
        // 尝试从缓存获取
        String cacheKey = "question:" + id;
        Object object = redisTemplate.opsForValue().get(cacheKey);
        ObjectMapper objectMapper = new ObjectMapper();
        if (object != null) {
            return objectMapper.convertValue(object, Question.class);
        }
        // 从数据库获取并缓存
        Question question = questionMapper.selectById(id);
        if (question != null) {
            redisTemplate.opsForValue().set(cacheKey, question);
        }
        return question;
    }

    @Override
    public List<Question> getQuestionsByKnowledgePointId(Long knowledgePointId) {
        // 尝试从缓存获取
        String cacheKey = "questions:knowledgePoint:" + knowledgePointId;
        List<?> cachedQuestions = (List<?>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedQuestions != null) {
            // 将缓存中的数据转换为 List<Question>
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.convertValue(cachedQuestions, objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class));
            } catch (Exception e) {
                // 记录日志
                log.error("Failed to convert cached questions to List<Question> for knowledgePointId: {}", knowledgePointId, e);
            }
        }

        // 从数据库获取并缓存
        List<Question> questions = questionMapper.getQuestionsByKnowledgePointId(knowledgePointId);
        if (questions != null && !questions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, questions);
        }
        return questions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        // 尝试从缓存获取
        String cacheKey = "questions:all";
        List<?> cachedQuestions = (List<?>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedQuestions != null) {
            // 将缓存中的数据转换为 List<Question>
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.convertValue(cachedQuestions, objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class));
            } catch (Exception e) {
                // 记录日志
                log.error("Failed to convert cached questions to List<Question> for all questions", e);
            }
        }

        // 从数据库获取并缓存
        List<Question> questions = questionMapper.selectAll();
        if (questions != null && !questions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, questions);
        }
        return questions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestionsByQuestionBodyId(Long questionBodyId) {
        // 尝试从缓存获取
        String cacheKey = "questions:questionBody:" + questionBodyId;
        List<?> cachedQuestions = (List<?>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedQuestions != null) {
            // 将缓存中的数据转换为 List<Question>
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.convertValue(cachedQuestions, objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class));
            } catch (Exception e) {
                // 记录日志
                log.error("Failed to convert cached questions to List<Question> for questionBodyId: {}", questionBodyId, e);
            }
        }

        // 从数据库获取并缓存
        List<Question> questions = questionMapper.getQuestionsByQuestionBodyId(questionBodyId);
        if (questions != null && !questions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, questions);
        }
        return questions;
    }

    @Override
    public void syncToRedis(Question question) {
        String cacheKey = "question:" + question.getId();
        redisTemplate.opsForValue().set(cacheKey, question);
    }
}

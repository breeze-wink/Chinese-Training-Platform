package com.example.service.question.impl;

import com.example.mapper.question.QuestionMapper;
import com.example.model.question.Question;
import com.example.service.question.QuestionService;
import com.example.service.question.QuestionStatisticService;
import com.example.service.question.UploadQuestionService;
import com.example.service.rabbitmq.RabbitMQProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionMapper questionMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitMQProducer rabbitMQProducer;
    private final QuestionStatisticService questionStatisticService;
    private final UploadQuestionService uploadQuestionService;

    @Autowired
    public QuestionServiceImpl(QuestionMapper questionMapper,
                               RedisTemplate<String, Object> redisTemplate,
                               RabbitMQProducer rabbitMQProducer,
                               QuestionStatisticService questionStatisticService,
                               UploadQuestionService uploadQuestionService) {
        this.questionMapper = questionMapper;
        this.redisTemplate = redisTemplate;
        this.rabbitMQProducer = rabbitMQProducer;
        this.questionStatisticService = questionStatisticService;
        this.uploadQuestionService = uploadQuestionService;
    }

    @Override
    @Transactional
    public int createQuestion(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    @Transactional
    public void access(Question question) {
        questionMapper.access(question.getId());
        // 发送同步消息
        rabbitMQProducer.sendQuestionSyncMessage(question, RabbitMQProducer.CREATE_OPERATION);
    }
    @Override
    @Transactional
    public int deleteQuestion(Question question) {
        Long id = question.getId();
        if (checkQuestionNotUsed(question)) {
            questionStatisticService.delete(id, "small");
            uploadQuestionService.delete(id, "small");
            rabbitMQProducer.sendQuestionSyncMessage(question, RabbitMQProducer.DELETE_OPERATION);
            return questionMapper.reallyDelete(id);
        }
        int result = questionMapper.delete(id);
        // 发送同步消息
        rabbitMQProducer.sendQuestionSyncMessage(question, RabbitMQProducer.DELETE_OPERATION);
        if (question.getBodyId() != null) {
            String cacheKey = "questions:questionBody:" + question.getBodyId();
            redisTemplate.delete(cacheKey);
        }
        return result;
    }

    private boolean checkQuestionNotUsed(Question question) {
        //TODO:
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestionById(Long id) {
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
    @Transactional(readOnly = true)
    public List<Question> getQuestionsByIds(List<Long> questionIds) {
        List<Question> questions = new ArrayList<>();
        for (Long id : questionIds) {
            questions.add(getQuestionById(id));
        }
        return questions;
    }

    @Override
    @Transactional
    @Async
    public void flushKnowledgePointCache(Long id) {
        String cacheKey = "questions:knowledgePoint:" + id;
        List<Question> questions = questionMapper.getQuestionsByKnowledgePointId(id);
        if (questions != null && !questions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, questions);
        }
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
    public List<Question> getAllQuestions() {
        return questionMapper.selectAll();
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
    @Transactional(readOnly = true)
    public List<Question> getQuestionsByKnowledgePointIds(List<Long> knowledgePointIds) {
        List<Question> questions = new ArrayList<>();
        for (Long knowledgePointId : knowledgePointIds) {
            questions.addAll(getQuestionsByKnowledgePointId(knowledgePointId));
        }
        return questions;
    }


    @Override
    public void deleteFromRedis(Question question) {
        String cacheKey = "question:" + question.getId();
        redisTemplate.delete(cacheKey);
        cacheKey = "questions:knowledgePoint:" + question.getKnowledgePointId();
        redisTemplate.delete(cacheKey);
    }
    @Override
    public void syncToRedis(Question question) {
        String cacheKey = "question:" + question.getId();
        redisTemplate.opsForValue().set(cacheKey, question);
        cacheKey = "questions:knowledgePoint:" + question.getKnowledgePointId();
        redisTemplate.delete(cacheKey);
    }
}

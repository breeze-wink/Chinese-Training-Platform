package com.example.service.question.impl;

import com.example.mapper.question.QuestionBodyMapper;
import com.example.model.question.Question;
import com.example.model.question.QuestionBody;
import com.example.service.question.QuestionBodyService;
import com.example.service.question.QuestionService;
import com.example.service.rabbitmq.RabbitMQProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.dto.redis.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class QuestionBodyServiceImpl implements QuestionBodyService {

    private final QuestionBodyMapper questionBodyMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitMQProducer rabbitMQProducer;
    private final QuestionService questionService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson 的 ObjectMapper，用于序列化和反序列化

    @Autowired
    public QuestionBodyServiceImpl(QuestionBodyMapper questionBodyMapper,
                                   RedisTemplate<String, Object> redisTemplate,
                                   RabbitMQProducer rabbitMQProducer,
                                   QuestionService questionService) {
        this.questionBodyMapper = questionBodyMapper;
        this.redisTemplate = redisTemplate;
        this.rabbitMQProducer = rabbitMQProducer;
        this.questionService = questionService;
    }

    @Override
    @Transactional
    public int createQuestionBody(QuestionBody questionBody) {
        int result = questionBodyMapper.insert(questionBody);
        rabbitMQProducer.sendQuestionBodySyncMessage(questionBody, RabbitMQProducer.CREATE_OPERATION);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionBody getQuestionBodyById(Long id) {
        String cashKey = "questionBody:" + id;
        Object object = redisTemplate.opsForValue().get(cashKey);
        ObjectMapper objectMapper = new ObjectMapper();
        if (object != null) {
            return objectMapper.convertValue(object, QuestionBody.class);
        }

        QuestionBody questionBody = questionBodyMapper.selectById(id);
        if (questionBody != null) {
            redisTemplate.opsForValue().set(cashKey, questionBody);
        }
        return questionBody;
    }

    @Override
    @Transactional
    public List<QuestionBody> getQuestionBodiesByIds(ArrayList<Long> ids) {
        List<QuestionBody> questionBodies = new ArrayList<>();
        for (Long id : ids) {
            questionBodies.add(getQuestionBodyById(id));
        }
        return questionBodies;
    }

    @Override
    @Transactional
    public List<PreassembledPracticeQuestion> getPreassembledQuestionsByTypes(List<String> types) {
        List<PreassembledPracticeQuestion> result = new ArrayList<>();
        for (String type : types) {
            String cacheKey = "preassembled_questions:" + type;
            Object object = redisTemplate.opsForValue().get(cacheKey);
            List<PreassembledPracticeQuestion> cachedQuestions = objectMapper.convertValue(object
                    ,objectMapper.getTypeFactory().constructCollectionType(List.class, PreassembledPracticeQuestion.class));

            if (cachedQuestions == null) {
                // 如果缓存中没有，刷新缓存
                flushPreassembledQuestionsByType(type);
                objectMapper.convertValue(object
                        ,objectMapper.getTypeFactory().constructCollectionType(List.class, PreassembledPracticeQuestion.class));
            }
            if (cachedQuestions != null) {
                result.addAll(cachedQuestions);
            }
        }
        return result;
    }

    @Transactional
    @Override
    public void flushPreassembledQuestions() {
        List<String> allTypes = questionBodyMapper.getAllTypes();
        for (String type : allTypes) {
            flushPreassembledQuestionsByType(type);
        }
    }

    @Transactional
    protected void flushPreassembledQuestionsByType(String type) {
        String cacheKey = "preassembled_questions:" + type;
        List<PreassembledPracticeQuestion> preassembledQuestions = assemblePreassembledQuestionsByType(type);
        if (preassembledQuestions != null && !preassembledQuestions.isEmpty()) {
            // 将预组装的题目列表存入 Redis，设置过期时间，例如 1 小时
            redisTemplate.opsForValue().set(cacheKey, preassembledQuestions, 1, TimeUnit.HOURS);
        } else {
            // 如果没有题目，删除缓存
            redisTemplate.delete(cacheKey);
        }
    }


    @Transactional
    protected List<PreassembledPracticeQuestion> assemblePreassembledQuestionsByType(String type) {
        List<PreassembledPracticeQuestion> preassembledQuestions = new ArrayList<>();
        // 获取指定类型的所有 QuestionBody
        List<QuestionBody> questionBodies = getQuestionBodiesByType(type);
        if (questionBodies == null || questionBodies.isEmpty()) {
            return preassembledQuestions;
        }

        for (QuestionBody questionBody : questionBodies) {
            // 获取与 QuestionBody 关联的所有 Question
            List<Question> questions = questionService.getQuestionsByQuestionBodyId(questionBody.getId());
            if (questions == null || questions.isEmpty()) {
                continue;
            }

            // 组装 PreassembledPracticeQuestion
            PreassembledPracticeQuestion preassembledQuestion = new PreassembledPracticeQuestion();
            preassembledQuestion.setQuestionBody(questionBody.getBody());

            List<SubQuestion> subQuestions = questions.stream().map(question -> {
                SubQuestion subQuestion = new SubQuestion();
                subQuestion.setQuestionId(question.getId());
                subQuestion.setQuestionContent(question.getContent());
                subQuestion.setType(question.getType());
                if ("CHOICE".equals(question.getType())) {
                    subQuestion.setQuestionOptions(question.getOptions());
                }
                return subQuestion;
            }).collect(Collectors.toList());
            preassembledQuestion.setType(type);
            preassembledQuestion.setSubQuestions(subQuestions);
            preassembledQuestions.add(preassembledQuestion);
        }
        return preassembledQuestions;
    }

    @Transactional
    @Override
    public List<QuestionBody> getAllQuestionBodies() {
        return questionBodyMapper.selectAll();
    }

    @Transactional
    @Override
    public int updateQuestionBody(QuestionBody questionBody) {
        int result = questionBodyMapper.update(questionBody);
        rabbitMQProducer.sendQuestionBodySyncMessage(questionBody, RabbitMQProducer.CREATE_OPERATION);
        return result;
    }

    @Override
    @Transactional
    public int deleteQuestionBody(Long id) {
        QuestionBody questionBody = getQuestionBodyById(id);
        int result = questionBodyMapper.delete(id);
        if (questionBody != null) {
            rabbitMQProducer.sendQuestionBodySyncMessage(questionBody, RabbitMQProducer.DELETE_OPERATION);
        }
        return result;
    }

    @Override
    @Transactional
    public List<QuestionBody> getQuestionBodiesByType(String type) {
        return questionBodyMapper.getQuestionBodiesByType(type);
    }

    @Override
    public void syncToRedis(QuestionBody questionBody) {
        String cacheKey = "questionBody:" + questionBody.getId();
        redisTemplate.opsForValue().set(cacheKey, questionBody);
    }

    @Override
    public void deleteFromRedis(Long id) {
        String cacheKey = "questionBody:" + id;
        redisTemplate.delete(cacheKey);
    }


}

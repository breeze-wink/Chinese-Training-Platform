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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class QuestionBodyServiceImpl implements QuestionBodyService {

    private final QuestionBodyMapper questionBodyMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitMQProducer rabbitMQProducer;
    private final QuestionService questionService;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    public List<String> getAllTypes() {
        return questionBodyMapper.getAllTypes();
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

package com.example.service.question.impl;

import com.example.mapper.question.QuestionBodyMapper;
import com.example.model.question.Question;
import com.example.model.question.QuestionBody;
import com.example.model.view.QuestionUsageView;
import com.example.service.question.QuestionBodyService;
import com.example.service.question.QuestionService;
import com.example.service.question.QuestionStatisticService;
import com.example.service.question.UploadQuestionService;
import com.example.service.rabbitmq.RabbitMQProducer;
import com.example.service.view.QuestionUsageViewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QuestionBodyServiceImpl implements QuestionBodyService {

    private final QuestionBodyMapper questionBodyMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitMQProducer rabbitMQProducer;
    private final QuestionService questionService;
    private final UploadQuestionService uploadQuestionService;
    private final QuestionUsageViewService questionUsageViewService;
    private final QuestionStatisticService questionStatisticService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public QuestionBodyServiceImpl(QuestionBodyMapper questionBodyMapper,
                                   RedisTemplate<String, Object> redisTemplate,
                                   RabbitMQProducer rabbitMQProducer,
                                   QuestionService questionService,
                                   UploadQuestionService uploadQuestionService,
                                   QuestionStatisticService questionStatisticService,
                                   QuestionUsageViewService questionUsageViewService) {
        this.questionBodyMapper = questionBodyMapper;
        this.redisTemplate = redisTemplate;
        this.rabbitMQProducer = rabbitMQProducer;
        this.questionService = questionService;
        this.uploadQuestionService = uploadQuestionService;
        this.questionStatisticService = questionStatisticService;
        this.questionUsageViewService = questionUsageViewService;
    }

    @Override
    @Transactional
    public int createQuestionBody(QuestionBody questionBody) {
        return questionBodyMapper.insert(questionBody);
    }

    @Override
    @Transactional
    public void access(QuestionBody questionBody) {
        questionBodyMapper.access(questionBody.getId());
        rabbitMQProducer.sendQuestionBodySyncMessage(questionBody, RabbitMQProducer.CREATE_OPERATION);

    }

    @Override
    @Transactional
    public void deny(QuestionBody questionBody) {
        List<Question> subQuestions = questionService.getQuestionsByQuestionBodyId(questionBody.getId());
        for (Question subQuestion : subQuestions) {
            questionService.deny(subQuestion);
            rabbitMQProducer.sendQuestionSyncMessage(subQuestion, RabbitMQProducer.CREATE_OPERATION);
        }

        questionBodyMapper.deny(questionBody.getId());
        rabbitMQProducer.sendQuestionBodySyncMessage(questionBody, RabbitMQProducer.CREATE_OPERATION);
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
            QuestionBody questionBody = getQuestionBodyById(id);
            if (questionBody != null) {
                questionBodies.add(getQuestionBodyById(id));

            }
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
    public int deleteQuestionBody(Long id) throws JsonProcessingException {
        QuestionBody questionBody = getQuestionBodyById(id);
        List<Question> subQuestions = questionService.getQuestionsByQuestionBodyId(id);
        int result;
        if (checkQuestionBodyNotUsed(questionBody)) {
            uploadQuestionService.delete(id, "big");
            questionStatisticService.delete(id, "big");
            result =  questionBodyMapper.reallyDelete(id);
            questionService.fileRemove(questionBody.getBody());

            for (Question subQuestion : subQuestions) {
                questionService.deleteQuestion(subQuestion);
                rabbitMQProducer.sendQuestionSyncMessage(subQuestion, RabbitMQProducer.DELETE_OPERATION);
            }
        }
        else {
            result = questionBodyMapper.delete(id);
            for (Question subQuestion : subQuestions) {
                questionService.outdateQuestion(subQuestion);
                rabbitMQProducer.sendQuestionSyncMessage(subQuestion, RabbitMQProducer.DELETE_OPERATION);
            }
        }

        rabbitMQProducer.sendQuestionBodySyncMessage(questionBody, RabbitMQProducer.DELETE_OPERATION);
        String cacheKey = "questions:questionBody:" + id;
        redisTemplate.delete(cacheKey);
        return result;
    }


    private boolean checkQuestionBodyNotUsed(QuestionBody questionBody) {

        /*判断是否被试卷和练习引用过*/
        QuestionUsageView questionUsageView = questionUsageViewService.getQuestionUsageByIdAndType(questionBody.getId(),"big");

        return !questionUsageView.getUsed();
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

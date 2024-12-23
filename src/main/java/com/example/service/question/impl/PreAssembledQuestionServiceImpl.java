package com.example.service.question.impl;

import com.example.dto.redis.PreAssembledQuestion;
import com.example.dto.redis.SubQuestion;
import com.example.model.question.Question;
import com.example.model.question.QuestionBody;
import com.example.service.course.KnowledgePointService;
import com.example.service.question.PreAssembledQuestionService;
import com.example.service.question.QuestionBodyService;
import com.example.service.question.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PreAssembledQuestionServiceImpl implements PreAssembledQuestionService {
    private final RedisTemplate<String, Object> redisTemplate;

    private final QuestionService questionService;
    private final QuestionBodyService questionBodyService;
    private final KnowledgePointService knowledgePointService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public PreAssembledQuestionServiceImpl(RedisTemplate<String, Object> redisTemplate,
                                           QuestionService questionService,
                                           KnowledgePointService knowledgePointService,
                                           QuestionBodyService questionBodyService) {
        this.redisTemplate = redisTemplate;
        this.questionService = questionService;
        this.questionBodyService = questionBodyService;
        this.knowledgePointService = knowledgePointService;
    }

    @Override
    @Transactional
    public List<PreAssembledQuestion> getPreAssembledQuestionsByTypes(List<String> types) {
        List<PreAssembledQuestion> result = new ArrayList<>();
        for (String type : types) {
            List<PreAssembledQuestion> cachedQuestions = getPreAssembledQuestionsByType(type);


            if (cachedQuestions != null) {
                result.addAll(cachedQuestions);
            }
        }
        return result;
    }


    @Transactional
    @Override
    public List<PreAssembledQuestion> getPreAssembledQuestionsByType(String type) {
        String cacheKey = "preassembled_questions:" + type;
        Object object = redisTemplate.opsForValue().get(cacheKey);
        List<PreAssembledQuestion> cachedQuestions = objectMapper.convertValue(object
                ,objectMapper.getTypeFactory().constructCollectionType(List.class, PreAssembledQuestion.class));
        if (cachedQuestions == null) {
            // 如果缓存中没有，刷新缓存
            flushPreAssembledQuestionsByType(type);
            object = redisTemplate.opsForValue().get(cacheKey);
            cachedQuestions = objectMapper.convertValue(object
                    ,objectMapper.getTypeFactory().constructCollectionType(List.class, PreAssembledQuestion.class));
        }
        return cachedQuestions;
    }

    @Transactional
    @Override
    public void flushPreAssembledQuestions() {

        List<String> allTypes = knowledgePointService.getAllTypes();
        for (String type : allTypes) {
            flushPreAssembledQuestionsByType(type);
        }
    }

    @Transactional
    @Override
    @Async
    public void flushPreAssembledQuestionsByType(String type) {
        String cacheKey = "preassembled_questions:" + type;
        List<PreAssembledQuestion> preassembledQuestions = assembleQuestionsByType(type);
        if (preassembledQuestions != null && !preassembledQuestions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, preassembledQuestions, 1, TimeUnit.HOURS);
        } else {
            // 如果没有题目，删除缓存
            redisTemplate.delete(cacheKey);
        }
    }


    @Transactional(readOnly = true)
    protected List<PreAssembledQuestion> assembleQuestionsByType(String type) {
        List<PreAssembledQuestion> preassembledQuestions = new ArrayList<>();
        // 获取指定类型的所有 QuestionBody
        //作文需要特殊处理
        if (type.equals("作文")) {
            List<Question> questions = questionService.getQuestionsByKnowledgePointId(138L);
            for (Question question : questions) {
                PreAssembledQuestion preassembledQuestion = new PreAssembledQuestion();
                preassembledQuestion.setId(question.getId());
                preassembledQuestion.setType("作文");
                SubQuestion subQ = new SubQuestion();
                subQ.setQuestionId(question.getId());
                subQ.setType(question.getType());
                subQ.setQuestionContent(question.getContent());
                subQ.setQuestionExplanation(question.getAnswer().split("\\$\\$")[1]);
                String knowledgePoint = knowledgePointService.getKnowledgePointNameById(question.getKnowledgePointId());
                subQ.setKnowledgePoint(knowledgePoint);
                preassembledQuestion.setSubQuestions(List.of(subQ));
                preassembledQuestions.add(preassembledQuestion);
            }
            return preassembledQuestions;
        }
        //其他
        else {
            List<QuestionBody> questionBodies = questionBodyService.getQuestionBodiesByType(type);
            if (questionBodies == null || questionBodies.isEmpty()) {
                return preassembledQuestions;
            }

            for (QuestionBody questionBody : questionBodies) {
                // 获取与 QuestionBody 关联的所有 Question
                List<Question> questions = questionService.getQuestionsByQuestionBodyId(questionBody.getId());
                if (questions == null || questions.isEmpty()) {
                    continue;
                }

                // 组装 PreAssembledQuestion
                PreAssembledQuestion preassembledQuestion = new PreAssembledQuestion();
                preassembledQuestion.setId(questionBody.getId());
                preassembledQuestion.setQuestionBody(questionBody.getBody());

                List<SubQuestion> subQuestions = questions.stream().map(question -> {
                    SubQuestion subQuestion = new SubQuestion();
                    subQuestion.setQuestionId(question.getId());
                    subQuestion.setQuestionContent(question.getContent());
                    subQuestion.setType(question.getType());
                    if ("CHOICE".equals(question.getType())) {
                        subQuestion.setQuestionOptions(question.getOptions());
                    }
                    String temp = question.getAnswer();
                    String[] temps = temp.split("\\$\\$");
                    String answer = temps[0];
                    if (question.getType().equals("FILL_IN_BLANK")) {
                        answer = answer.replaceAll("##", ";");
                    }
                    subQuestion.setQuestionAnswer(answer);
                    if (temps.length > 1) {
                        String explanation = temps[1];
                        subQuestion.setQuestionExplanation(explanation);
                    }
                    String knowledgePoint = knowledgePointService.getKnowledgePointNameById(question.getKnowledgePointId());
                    subQuestion.setKnowledgePoint(knowledgePoint);
                    return subQuestion;
                }).collect(Collectors.toList());
                preassembledQuestion.setType(type);
                preassembledQuestion.setSubQuestions(subQuestions);
                preassembledQuestions.add(preassembledQuestion);
            }
        }
        return preassembledQuestions;
    }
}

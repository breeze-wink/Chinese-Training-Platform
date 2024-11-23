package com.example.service.question.impl;


import com.example.mapper.question.QuestionMapper;
import com.example.model.question.Question;
import com.example.service.question.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionMapper questionMapper;
    public QuestionServiceImpl(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }
    @Override
    @Transactional
    public int creatQuestion(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    @Transactional
    public int deleteQuestion(Long id) {
        return questionMapper.delete(id);
    }

    @Override
    @Transactional
    public Question getQuestionById(Long id) {
        return questionMapper.selectById(id);
    }

    @Override
    @Transactional
    public List<Question> getQuestionsByKnowledgePointId(Long knowledgePointId) {
        return questionMapper.getQuestionsByKnowledgePointId(knowledgePointId);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionMapper.selectAll();
    }
}

package com.example.service.question.impl;

import com.example.mapper.question.QuestionBodyMapper;
import com.example.model.question.QuestionBody;
import com.example.service.question.QuestionBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionBodyServiceImpl implements QuestionBodyService {

    private final QuestionBodyMapper questionBodyMapper;

    @Autowired
    public QuestionBodyServiceImpl(QuestionBodyMapper questionBodyMapper) {
        this.questionBodyMapper = questionBodyMapper;
    }

    @Override
    @Transactional
    public int createQuestionBody(QuestionBody questionBody) {
        return questionBodyMapper.insert(questionBody);
    }

    @Override
    @Transactional
    public QuestionBody getQuestionBodyById(Long id) {
        return questionBodyMapper.selectById(id);
    }

    @Transactional
    @Override
    public List<QuestionBody> getAllQuestionBodies() {
        return questionBodyMapper.selectAll();
    }

    @Transactional
    @Override
    public int updateQuestionBody(QuestionBody questionBody) {
        return questionBodyMapper.update(questionBody);
    }

    @Override
    @Transactional
    public int deleteQuestionBody(Long id) {
        return questionBodyMapper.delete(id);
    }

    @Override
    @Transactional
    public List<QuestionBody> getQuestionBodiesByType(String type) {
        return questionBodyMapper.getQuestionBodiesByType(type);
    }
}

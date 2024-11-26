package com.example.service.question.impl;

import com.example.mapper.question.QuestionBodyMapper;
import com.example.model.question.QuestionBody;
import com.example.service.question.QuestionBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBodyServiceImpl implements QuestionBodyService {

    private final QuestionBodyMapper questionBodyMapper;

    @Autowired
    public QuestionBodyServiceImpl(QuestionBodyMapper questionBodyMapper) {
        this.questionBodyMapper = questionBodyMapper;
    }

    @Override
    public int createQuestionBody(QuestionBody questionBody) {
        return questionBodyMapper.insert(questionBody);
    }

    @Override
    public QuestionBody getQuestionBodyById(Long id) {
        return questionBodyMapper.selectById(id);
    }

    @Override
    public List<QuestionBody> getAllQuestionBodies() {
        return questionBodyMapper.selectAll();
    }

    @Override
    public int updateQuestionBody(QuestionBody questionBody) {
        return questionBodyMapper.update(questionBody);
    }

    @Override
    public int deleteQuestionBody(Long id) {
        return questionBodyMapper.delete(id);
    }
}

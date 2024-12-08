package com.example.service.question.impl;

import com.example.model.question.QuestionStatistic;
import com.example.mapper.question.QuestionStatisticMapper;
import com.example.service.question.QuestionStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionStatisticServiceImpl implements QuestionStatisticService {

    @Autowired
    private QuestionStatisticMapper questionStatisticMapper;

    @Override
    public void insert(QuestionStatistic questionStatistic) {
        questionStatisticMapper.insert(questionStatistic);
    }

    @Override
    public void update(QuestionStatistic questionStatistic) {
        questionStatisticMapper.update(questionStatistic);
    }

    @Override
    public void delete(Long id, String type) {
        questionStatisticMapper.delete(id, type);
    }

    @Override
    public QuestionStatistic findByIdAndType(Long id, String type) {
        return questionStatisticMapper.findByIdAndType(id, type);
    }

    @Override
    public List<QuestionStatistic> findAll() {
        return questionStatisticMapper.findAll();
    }
}
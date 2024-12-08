package com.example.service.question;

import com.example.model.question.QuestionStatistic;

import java.util.List;

public interface QuestionStatisticService {
    void insert(QuestionStatistic questionStatistic);
    void update(QuestionStatistic questionStatistic);
    void delete(Long id, String type);
    QuestionStatistic findByIdAndType(Long id, String type);
    List<QuestionStatistic> findAll();
}
package com.example.service.question;

import com.example.dto.mapper.QuestionStatisticDTO;
import com.example.model.question.QuestionStatistic;

import java.util.List;

public interface QuestionStatisticService {
    void insert(QuestionStatistic questionStatistic);
    void update(QuestionStatistic questionStatistic);
    void delete(Long id, String type);

    void addReferencedCount(List<QuestionStatisticDTO> questionStatisticDTOS);
    QuestionStatistic findByIdAndType(Long id, String type);
    List<QuestionStatistic> findAll();
}
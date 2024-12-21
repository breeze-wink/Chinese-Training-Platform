package com.example.service.question;

import com.example.dto.mapper.QuestionStatisticDTO;
import com.example.model.question.QuestionStatistic;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface QuestionStatisticService {
    void insert(QuestionStatistic questionStatistic);
    void update(QuestionStatistic questionStatistic);
    void delete(Long id, String type);

    void addReferencedCount(List<QuestionStatisticDTO> questionStatisticDTOS);
    QuestionStatistic findByIdAndType(Long id, String type);
    LocalDateTime getUploadTime(Long id, String type);
    List<QuestionStatistic> findAll();

    boolean checkQuestionPassed(Long questionId, String type);

    boolean checkQuestionWaiting(Long questionId, String type);

}
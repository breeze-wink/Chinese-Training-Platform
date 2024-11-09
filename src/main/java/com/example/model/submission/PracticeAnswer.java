package com.example.model.submission;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PracticeAnswer {
    private Long id;
    private Long practiceQuestionId;
    private String answerContent;
    private BigDecimal score;
    private String feedback;

}

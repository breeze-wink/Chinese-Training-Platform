package com.example.model.question;

import lombok.Data;

@Data
public class PracticeQuestion {
    private Long id;
    private Long practiceId;
    private Long questionId;
    private Integer sequence;
}
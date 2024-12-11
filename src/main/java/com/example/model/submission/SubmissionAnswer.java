package com.example.model.submission;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SubmissionAnswer {

    private Long id;
    private Long submissionId; // 外键，关联AssignmentSubmission表的id
    private Long questionId; // 外键，关联Question表的id
    private String answerContent;
    private BigDecimal score;
    private String feedback;
    private String sequence;

}

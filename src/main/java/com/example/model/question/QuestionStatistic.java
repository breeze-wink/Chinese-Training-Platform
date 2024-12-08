package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuestionStatistic {
    private Long id;
    private String type;
    private Date uploadTime;
    private Double totalScore;
    private Integer completeCount;
    private Integer referencedCount;
}

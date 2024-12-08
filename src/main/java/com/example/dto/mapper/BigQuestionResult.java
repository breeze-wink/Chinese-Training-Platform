package com.example.dto.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BigQuestionResult {
    private Long bodyId;
    private String body;
    private String type;
    private Double totalScore;
    private Integer completeCount;
    private Integer referencedCount;
    private Date uploadTime;
}


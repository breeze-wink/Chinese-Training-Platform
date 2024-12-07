package com.example.dto.mapper;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuestionResult {
    private Long questionId;
    private String body;
    private String content;
    private String answer;
    private String type;
    private Double totalScore;
    private Integer completeCount;
    private Integer referencedCount;
    private Date uploadTime;
    private String knowledgeName; // 新增字段
}

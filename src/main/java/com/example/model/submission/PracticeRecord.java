package com.example.model.submission;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class PracticeRecord {

    private Long id;
    private Long studentId; // 外键，关联Student表的id
    private Long knowledgePointId; // 外键，关联KnowledgePoint表的id
    private LocalDateTime practiceTime;
    private BigDecimal totalScore;

}
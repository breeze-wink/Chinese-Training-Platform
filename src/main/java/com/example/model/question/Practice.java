package com.example.model.question;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class Practice {

    private Long id;
    private Long studentId; // 外键，关联Student表的id
    private String name;
    private LocalDateTime practiceTime;
    private BigDecimal totalScore;

}
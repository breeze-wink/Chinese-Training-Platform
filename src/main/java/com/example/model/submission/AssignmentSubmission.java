package com.example.model.submission;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Setter
@Getter
public class AssignmentSubmission {

    private Long id;
    private Long assignmentId; // 外键，关联Assignment表的id
    private Long studentId; // 外键，关联Student表的id
    private LocalDateTime submitTime;
    private Integer totalScore;
    private Boolean graded;

}

package com.example.model.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AssignmentScoresView {
    private Long assignmentId;
    private Long studentId;
    private Long classId;
    private Long groupId;
    private Integer score;
}

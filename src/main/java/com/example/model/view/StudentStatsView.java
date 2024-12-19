package com.example.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentStatsView {
    private Long studentId;
    private Long classId;
    private Long knowledgePointId;
    private Long score;
    private Long totalScore;
    private String type;
}

package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GeneratePaperRequest {
    private String name;
    private Long creatorId;
    private Integer totalScore;
    private Double difficulty;
    private List<questionInfo> questions;

    @Setter
    @Getter
    public static class questionInfo {
        private Long id;
        private String type;
        private String score;
        private Integer sequence;
        private List<String> subScores;
    }
}

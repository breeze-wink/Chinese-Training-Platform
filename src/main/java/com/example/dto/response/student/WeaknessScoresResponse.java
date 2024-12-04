package com.example.dto.response.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeaknessScoresResponse {
    private String message;
    private List<infoData> data;

    @Setter
    @Getter
    public static class infoData {
        private String type;
        private String weaknessName;
        private Double weaknessScore;
    }
}

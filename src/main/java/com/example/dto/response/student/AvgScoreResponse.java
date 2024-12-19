package com.example.dto.response.student;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AvgScoreResponse {
    private String message;
    private infoData data;

    @Setter
    @Getter
    public static class infoData {
        private Double averageHomeworkScore;
        private Long classRank;
    }
}

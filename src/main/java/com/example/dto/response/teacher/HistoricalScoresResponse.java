package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HistoricalScoresResponse {
    private String message;
    private BigDecimal avgScore;
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private BigDecimal score;
        private String endTime;
    }
}

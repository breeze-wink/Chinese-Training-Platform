package com.example.dto.response.student;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultidimensionalScoresResponse {
    private String message;
    private List<infoData> data;

    @Setter
    @Getter
    public static class infoData {
        private String name;
        private Double score;
    }
}

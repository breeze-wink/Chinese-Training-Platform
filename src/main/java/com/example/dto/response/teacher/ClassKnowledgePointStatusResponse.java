package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ClassKnowledgePointStatusResponse {
    private String message;
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private String name;
        private BigDecimal score;
    }
}

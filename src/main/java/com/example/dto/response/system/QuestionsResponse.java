package com.example.dto.response.system;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestionsResponse {
    private String message;
    private List<questionInfo> questions;

    @Setter
    @Getter
    public static class questionInfo {
        private Long id;
        private String title;
        private String description;
    }
}

package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneratePaperWithTypesResponse {
    private String message;
    private List<QuestionInfo> questions;

    @Setter
    @Getter
    public static class QuestionInfo{
        private String body;
        private List<SubQuestion> questions;
    }

    @Setter
    @Getter
    public static class SubQuestion{
        private String content;
        private String answer;
        private String explanation;
        private String type;
        private String knowledgePoint;
        private List<String> options;
    }
}

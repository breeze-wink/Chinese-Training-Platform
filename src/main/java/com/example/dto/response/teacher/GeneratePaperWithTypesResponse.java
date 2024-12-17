package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneratePaperWithTypesResponse {
    private String message;
    private List<QuestionInfo> questions;
    private List<Essay> essay;

    @Setter
    @Getter
    public static class QuestionInfo{
        private Long id;
        private String body;
        private Double difficulty;
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

    @Setter
    @Getter
    public static class Essay {
        private Long id;
        private String content;
        private String explanation;
        private String type;
        private Double difficulty;
        private String knowledgePoint;
    }
}

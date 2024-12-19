package com.example.dto.response.teacher;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchQuestionsResponse {
    private Integer totalCount;
    private Integer pageSize;
    private Integer currentPage;
    private Integer totalPages;
    private List<BigQuestion> bigQuestions = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();

    @Data
    public static class BigQuestion {
        private Long bodyId;
        private String body;
        private List<SubQuestion> subQuestion = new ArrayList<>();
        private Double difficulty;
        private Integer referencedCount;
    }

    @Data
    public static class SubQuestion {
        private String question;
        private String answer;
        private String explanation;
        private List<String> options;
        private String type;
        private String knowledge;
    }

    @Data
    public static class Question {
        private Long questionId;
        private String body;
        private String question;
        private String answer;
        private List<String> options;
        private String explanation;
        private Integer referencedCount;
        private Double difficulty;
        private String type;
        private String knowledge;
    }
}


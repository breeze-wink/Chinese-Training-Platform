package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetSubmissionResponse {
    private String message;
    private Integer totalScore;
    private List<QuestionInfo> questions;

    @Getter
    @Setter
    public static class QuestionInfo {
        private Long submissionAnswerId;
        private String body;
        private Integer sequence;
        private Integer score;
        private List<SubQuestionInfo> subQuestions;
        private String question;
        private String answer;
        private String explanation;
        private List<String> options;
        private String type;
        private String studentAnswer;
    }

    @Setter
    @Getter
    public static class SubQuestionInfo {
        private Long submissionAnswerId;
        private String question;
        private String answer;
        private String explanation;
        private List<String> options;
        private String type;
        private Integer subScore;
        private String studentAnswer;
    }
}

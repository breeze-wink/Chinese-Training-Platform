package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class GetUploadedQuestionResponse {
    private String message;
    private List<UploadQuestionInfo> uploadedQuestions;

    @Setter
    @Getter
    public static class UploadQuestionInfo {
        private String uploadTime;
        private String body;
        private List<subQuestion> subQuestions;
        private String content;
        private String answer;
        private String explanation;
        private List<String> options;
        private String type;
        private String knowledgePoint;
    }

    @Setter
    @Getter
    public static class subQuestion {
        private String content;
        private String answer;
        private String explanation;
        private List<String> options;
        private String type;
        private String knowledgePoint;
    }
}

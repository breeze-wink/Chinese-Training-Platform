package com.example.dto.response.teacher;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetQuestionResponse {
    private String message;
    private String body;

    /** 以下部分 为小题时的response */
    private String content;
    private String answer;
    private String explanation;
    private List<String> options;
    private String type;
    private String knowledgePoint;

    /** 以下部分 为大题时的response */
    private List<subQuestion> subQuestions;

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

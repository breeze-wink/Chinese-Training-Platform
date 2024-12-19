package com.example.dto.response.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomeworkAnswerResponse {
    private String message;
    private List<infoData> data;

    @Setter
    @Getter
    public static class infoData {
        private String sequence;
        private String body;
        private String questionContent;
        private String questionType;
        private List<String> questionOptions;
        private String answer;
        private String analysis;
        private String studentAnswer;
        private Integer score;
        private String feedback;
    }
}

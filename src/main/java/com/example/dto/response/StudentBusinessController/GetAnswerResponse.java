package com.example.dto.response.StudentBusinessController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAnswerResponse {
    private String message;
    private Double totalScore;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private String sequence;
        private String questionBody;
        private String questionContent;
        private String questionType;
        private List<String> questionOptions;
        private String answer;
        private String analysis;
        private String studentAnswer;
        private Double score;
    }
}

package com.example.dto.response.StudentBusinessController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContinuePracticeResponse {
    private String message;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long practiceQuestionId;
        private Integer sequence;
        private String questionContent;
        private String questionType;
        private String questionOptions;
        private String answerContent;
    }
}

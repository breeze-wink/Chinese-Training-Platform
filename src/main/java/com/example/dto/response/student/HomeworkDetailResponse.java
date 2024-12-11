package com.example.dto.response.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomeworkDetailResponse {
    private String message;
    private List<infoData> data;

    @Setter
    @Getter
    public static class infoData{
        private Long submissionAnswerId;
        private String sequence;
        private String body;
        private String questionContent;
        private String questionType;
        private List<String> questionOptions;
        private String answerContent;
    }
}

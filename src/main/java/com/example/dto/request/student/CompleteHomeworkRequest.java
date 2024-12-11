package com.example.dto.request.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompleteHomeworkRequest {
    private List<infoData> data;

    @Setter
    @Getter
    public static class infoData {
        private Long submissionAnswerId;
        private String answerContent;
    }
}

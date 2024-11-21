package com.example.dto.request.StudentBusinessController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveAnswerRequest {
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long practiceQuestionId;
        private String answerContent;
    }
}

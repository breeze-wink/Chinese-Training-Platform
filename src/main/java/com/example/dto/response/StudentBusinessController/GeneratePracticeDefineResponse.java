package com.example.dto.response.StudentBusinessController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneratePracticeDefineResponse {
    private String message;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long practiceQuestionId;
        private String questionContent;
        private String type;
        private String questionOptions;
        private Integer sequence;
    }
}

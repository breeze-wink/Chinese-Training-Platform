package com.example.dto.response.StudentController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneratePracticeDefineResponse {
    private Long practiceId;
    private String message;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long practiceQuestionId;
        private String questionBody;
        private String questionContent;
        private String type;
        private List<String> questionOptions;
        private String sequence;
    }
}

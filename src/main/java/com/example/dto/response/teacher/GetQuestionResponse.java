package com.example.dto.response.teacher;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetQuestionResponse {
    private String message;
    private String body;
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private String content;
        private String type;
        List<String> options;
        private String answer;
        private String analysis;
        private String knowledgePointName;
    }
}

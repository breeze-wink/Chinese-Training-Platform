package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllQuestionsResponse {
    private String message;
    private List<infoData> data;

    @Setter
    @Getter
    public static class infoData{
        private Long id;
        private String uploadTime;
        private String knowledgePoint;
        private String type;
    }
}

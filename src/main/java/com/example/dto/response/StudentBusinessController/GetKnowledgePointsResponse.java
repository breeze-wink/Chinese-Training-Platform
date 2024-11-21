package com.example.dto.response.StudentBusinessController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetKnowledgePointsResponse {
    private String message;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long id;
        private String name;
        private String description;
        private String type;
    }
}

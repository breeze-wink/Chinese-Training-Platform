package com.example.dto.response.TeacherController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class KnowledgePointsResponse {
    private String message;
    private Map<String, List<KnowledgePointInfo>> knowledgePoints;

    @Setter
    @Getter
    public static class KnowledgePointInfo {
        private String name;
        private String type;
        private String description;
    }
}

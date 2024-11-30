package com.example.dto.response.system;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetAllKnowledgePointsResponse {
    private String message;
    private List<KnowledgePointInfo> knowledgePointInfos;

    @Getter
    @Setter
    public static class KnowledgePointInfo {
        private Long id;
        private String name;
        private String description;
        private String type;
    }
}

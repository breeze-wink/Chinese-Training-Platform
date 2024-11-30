package com.example.dto.response.SystemAdminController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryKnowledgePointResponse {
    private String message;
    private KnowledgePointInfo data;

    @Getter
    @Setter
    public static class KnowledgePointInfo {
        private String name;
        private String description;
    }

}

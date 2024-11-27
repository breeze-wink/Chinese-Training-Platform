package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ListKnowledgeResponse {
    private String message;
    private Map<String, List<KnowledgePointInfo>> knowledgePoints;

    @Setter
    @Getter
    public static class KnowledgePointInfo {
        private Long id;
        private String name;
    }
}

package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateKnowledgePointResponse {
    private String message;
    private Long knowledgePointId;
    private String type;
}

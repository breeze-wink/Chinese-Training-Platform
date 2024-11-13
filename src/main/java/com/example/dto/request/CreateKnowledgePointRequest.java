package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateKnowledgePointRequest {
    private String name;
    private String description;
}

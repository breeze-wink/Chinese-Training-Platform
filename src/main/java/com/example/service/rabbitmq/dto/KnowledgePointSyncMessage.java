package com.example.service.rabbitmq.dto;

import com.example.model.course.KnowledgePoint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KnowledgePointSyncMessage {
    private String operation;
    private KnowledgePoint knowledgePoint;
}

package com.example.service.rabbitmq.RabbitMQConsumer;

import com.example.config.RabbitMQConfig;
import com.example.service.course.KnowledgePointService;
import com.example.service.rabbitmq.RabbitMQProducer;
import com.example.service.rabbitmq.dto.KnowledgePointSyncMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgePointConsumer {
    private final KnowledgePointService knowledgePointService;
    private final ObjectMapper objectMapper;

    @Autowired
    public KnowledgePointConsumer(KnowledgePointService knowledgePointService,
                                  ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.knowledgePointService = knowledgePointService;
    }

    @RabbitListener(queues = RabbitMQConfig.KNOWLEDGE_POINT_QUEUE_NAME)
    public void receiveMessage(String message) throws JsonProcessingException {
        try {
            KnowledgePointSyncMessage knowledgePointMessage = objectMapper.readValue(message, KnowledgePointSyncMessage.class);
            if (knowledgePointMessage.getOperation().equals(RabbitMQProducer.CREATE_OPERATION)) {
                knowledgePointService.syncToRedis(knowledgePointMessage.getKnowledgePoint());
            }
            else if (knowledgePointMessage.getOperation().equals(RabbitMQProducer.DELETE_OPERATION)) {
                knowledgePointService.deleteFromRedis(knowledgePointMessage.getKnowledgePoint());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

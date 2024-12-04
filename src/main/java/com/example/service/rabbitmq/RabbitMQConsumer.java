package com.example.service.rabbitmq;
import com.example.config.RabbitMQConfig;
import com.example.model.question.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.service.question.QuestionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final QuestionService questionService;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitMQConsumer(QuestionService questionService, ObjectMapper objectMapper) {
        this.questionService = questionService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        try {
            Question question = objectMapper.readValue(message, Question.class);
            // 同步到 Redis
            questionService.syncToRedis(question);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

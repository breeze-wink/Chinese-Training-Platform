package com.example.service.rabbitmq.RabbitMQConsumer;
import com.example.config.RabbitMQConfig;
import com.example.model.question.Question;
import com.example.model.question.QuestionBody;
import com.example.service.question.QuestionBodyService;
import com.example.service.rabbitmq.RabbitMQProducer;
import com.example.service.rabbitmq.dto.QuestionBodySyncMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionBodyConsumer {

    private final QuestionBodyService questionBodyService;
    private final ObjectMapper objectMapper;

    @Autowired
    public QuestionBodyConsumer(QuestionBodyService questionBodyService, ObjectMapper objectMapper) {
        this.questionBodyService = questionBodyService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.QUESTION_BODY_QUEUE_NAME)
    public void receiveMessage(String message) {
        try {
            QuestionBodySyncMessage syncMessage = objectMapper.readValue(message, QuestionBodySyncMessage.class);
            QuestionBody questionBody = syncMessage.getQuestionBody();
            String operation = syncMessage.getOperation();
            if (RabbitMQProducer.CREATE_OPERATION.equals(operation)) {
                // 处理创建操作
                questionBodyService.syncToRedis(questionBody);
            } else if (RabbitMQProducer.DELETE_OPERATION.equals(operation)) {
                // 处理删除操作
                questionBodyService.deleteFromRedis(questionBody.getId());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

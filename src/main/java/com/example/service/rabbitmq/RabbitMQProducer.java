   package com.example.service.rabbitmq;

   import com.example.config.RabbitMQConfig;
   import com.example.model.course.KnowledgePoint;
   import com.example.model.question.Question;
   import com.example.model.question.QuestionBody;
   import com.example.service.rabbitmq.dto.KnowledgePointSyncMessage;
   import com.example.service.rabbitmq.dto.QuestionBodySyncMessage;
   import com.example.service.rabbitmq.dto.QuestionSyncMessage;
   import com.fasterxml.jackson.core.JsonProcessingException;
   import com.fasterxml.jackson.databind.ObjectMapper;
   import org.springframework.amqp.rabbit.core.RabbitTemplate;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.scheduling.annotation.Async;
   import org.springframework.stereotype.Service;

   import java.util.concurrent.CompletableFuture;

   @Service
   public class RabbitMQProducer {
       public static String CREATE_OPERATION = "create";
       public static String DELETE_OPERATION = "delete";
       private final RabbitTemplate rabbitTemplate;
       private final ObjectMapper objectMapper;

       @Autowired
       public RabbitMQProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
           this.rabbitTemplate = rabbitTemplate;
           this.objectMapper = objectMapper;
       }

       @Async
       public void sendQuestionSyncMessage(Question question, String operation) {
           try {
               QuestionSyncMessage syncMessage = new QuestionSyncMessage();
               syncMessage.setOperation(operation);
               syncMessage.setQuestion(question);
               String message = objectMapper.writeValueAsString(syncMessage);
               rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUESTION_ROUTING_KEY, message);
           } catch (JsonProcessingException e) {
               e.printStackTrace();
           }
       }

       @Async
       public void sendQuestionBodySyncMessage(QuestionBody questionBody, String operation) {
           try {
               QuestionBodySyncMessage syncMessage = new QuestionBodySyncMessage();
               syncMessage.setOperation(operation);
               syncMessage.setQuestionBody(questionBody);
               String message = objectMapper.writeValueAsString(syncMessage);
               rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUESTION_BODY_ROUTING_KEY, message);
           } catch (JsonProcessingException e) {
               e.printStackTrace();
           }

       }

       @Async
       public void sendKnowledgePointSyncMessage(KnowledgePoint knowledgePoint, String operation) {
            try{
                KnowledgePointSyncMessage knowledgePointSyncMessage = new KnowledgePointSyncMessage();
                knowledgePointSyncMessage.setKnowledgePoint(knowledgePoint);
                knowledgePointSyncMessage.setOperation(operation);
                String message = objectMapper.writeValueAsString(knowledgePointSyncMessage);
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.KNOWLEDGE_POINT_ROUTING_KEY, message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
       }
   }
   
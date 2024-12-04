   package com.example.service.rabbitmq;

   import com.example.config.RabbitMQConfig;
   import com.example.model.question.Question;
   import com.fasterxml.jackson.core.JsonProcessingException;
   import com.fasterxml.jackson.databind.ObjectMapper;
   import org.springframework.amqp.rabbit.core.RabbitTemplate;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.scheduling.annotation.Async;
   import org.springframework.stereotype.Service;

   import java.util.concurrent.CompletableFuture;

   @Service
   public class RabbitMQProducer {

       private final RabbitTemplate rabbitTemplate;
       private final ObjectMapper objectMapper;

       @Autowired
       public RabbitMQProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
           this.rabbitTemplate = rabbitTemplate;
           this.objectMapper = objectMapper;
       }

       @Async
       public void sendQuestionSyncMessage(Question question) {
           try {
               String message = objectMapper.writeValueAsString(question);
               rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
           } catch (JsonProcessingException e) {
               e.printStackTrace();
           }
       }
   }
   
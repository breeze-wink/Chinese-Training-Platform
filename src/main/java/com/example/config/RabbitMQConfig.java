package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUESTION_QUEUE_NAME = "sync_question_queue";
    public static final String QUESTION_BODY_QUEUE_NAME = "sync_question_body_queue";
    public static final String KNOWLEDGE_POINT_QUEUE_NAME = "sync_knowledge_point_queue";
    public static final String EXCHANGE_NAME = "sync_exchange";
    public static final String QUESTION_ROUTING_KEY = "sync_question_key";
    public static final String QUESTION_BODY_ROUTING_KEY = "sync_question_body_key";
    public static final String KNOWLEDGE_POINT_ROUTING_KEY = "sync_knowledge_point_key";

    @Bean
    public Queue syncQuestionQueue() {
        return new Queue(QUESTION_QUEUE_NAME, true);
    }

    @Bean
    public Queue syncQuestionBodyQueue() {
        return new Queue(QUESTION_BODY_QUEUE_NAME, true);
    }

    @Bean
    public Queue syncKnowledgePointQueue() {
        return new Queue(KNOWLEDGE_POINT_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange syncExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    // 绑定 QUESTION_QUEUE_NAME 到 EXCHANGE_NAME 并使用 QUESTION_ROUTING_KEY
    @Bean
    public Binding syncQuestionBinding() {
        return BindingBuilder.bind(syncQuestionQueue()).to(syncExchange()).with(QUESTION_ROUTING_KEY);
    }

    // 绑定 QUESTION_BODY_QUEUE_NAME 到 EXCHANGE_NAME 并使用 QUESTION_BODY_ROUTING_KEY
    @Bean
    public Binding syncQuestionBodyBinding() {
        return BindingBuilder.bind(syncQuestionBodyQueue()).to(syncExchange()).with(QUESTION_BODY_ROUTING_KEY);
    }

    @Bean
    public Binding syncKnowledgePointBinding() {
        return BindingBuilder.bind(syncKnowledgePointQueue()).to(syncExchange()).with(KNOWLEDGE_POINT_ROUTING_KEY);
    }


}
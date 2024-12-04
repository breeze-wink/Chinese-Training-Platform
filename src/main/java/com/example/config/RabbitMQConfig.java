package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "sync_queue";
    public static final String EXCHANGE_NAME = "sync_exchange";
    public static final String ROUTING_KEY = "sync_key";

    @Bean
    public Queue syncQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange syncExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding syncBinding() {
        return BindingBuilder.bind(syncQueue()).to(syncExchange()).with(ROUTING_KEY);
    }
}

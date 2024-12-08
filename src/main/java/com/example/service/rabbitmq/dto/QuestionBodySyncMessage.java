package com.example.service.rabbitmq.dto;

import com.example.model.question.QuestionBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionBodySyncMessage {
    private String operation;
    private QuestionBody questionBody;
}

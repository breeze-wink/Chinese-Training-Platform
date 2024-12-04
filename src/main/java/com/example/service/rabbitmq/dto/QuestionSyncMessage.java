package com.example.service.rabbitmq.dto;

import com.example.model.question.Question;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionSyncMessage {
    private String operation; //
    private Question question;

}

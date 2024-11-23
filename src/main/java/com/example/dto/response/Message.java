package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    public Message(){}

    public Message(String message) {
        this.message  = message;
    }

    private String message;
}

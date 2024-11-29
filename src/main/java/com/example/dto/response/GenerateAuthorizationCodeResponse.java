package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GenerateAuthorizationCodeResponse {
    private String message;
    private String code;
    private String createDate;
}

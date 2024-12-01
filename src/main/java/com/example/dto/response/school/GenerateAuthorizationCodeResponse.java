package com.example.dto.response.school;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateAuthorizationCodeResponse {
    private String message;
    private String code;
    private String createDate;
}

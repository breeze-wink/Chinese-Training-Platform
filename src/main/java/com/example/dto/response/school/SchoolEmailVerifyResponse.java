package com.example.dto.response.school;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolEmailVerifyResponse {
    private String message;
    private String verificationCode;
}

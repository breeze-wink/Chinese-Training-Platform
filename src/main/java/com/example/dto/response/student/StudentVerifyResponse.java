package com.example.dto.response.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentVerifyResponse {
    private String message;
    private String verificationCode;
}

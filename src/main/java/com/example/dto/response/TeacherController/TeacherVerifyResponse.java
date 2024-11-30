package com.example.dto.response.TeacherController;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherVerifyResponse {
    private String message;
    private String verificationCode;
    private Long schoolId;
}

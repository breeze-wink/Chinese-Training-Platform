package com.example.dto.response.StudentController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentChangeEmailVerificationResponse {
    private String message;
    private InfoData data;
    @Getter
    @Setter
    public static class InfoData {
        private String verificationCode;
    }
}

package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRegisterRequest {
    private String email;
    private String verificationCode;
    private String password;
    private String confirmPassword;
    private Long schoolId;
}

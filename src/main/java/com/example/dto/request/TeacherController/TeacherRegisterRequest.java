package com.example.dto.request.TeacherController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRegisterRequest {
    private String email;
    private String password;
    private String confirmPassword;
    private Long schoolId;
}

package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherChangePasswordRequest {
    private String password;
    private String newPassword;
}

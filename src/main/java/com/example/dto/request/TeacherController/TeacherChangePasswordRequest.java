package com.example.dto.request.TeacherController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherChangePasswordRequest {
    private String password;
    private String newPassword;
}

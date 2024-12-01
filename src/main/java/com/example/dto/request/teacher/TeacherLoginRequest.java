package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherLoginRequest {
    private String account;
    private String password;
}

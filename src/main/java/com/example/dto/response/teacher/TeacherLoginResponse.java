package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherLoginResponse {
    private String message;
    private Long id;
    private String token;
}

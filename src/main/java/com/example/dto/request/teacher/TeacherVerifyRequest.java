package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherVerifyRequest {
    private String authorizationCode;
    private String email;
}

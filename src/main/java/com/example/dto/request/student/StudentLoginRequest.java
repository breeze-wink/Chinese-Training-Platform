package com.example.dto.request.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLoginRequest {
    private String account;
    private String password;
}

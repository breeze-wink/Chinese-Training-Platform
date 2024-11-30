package com.example.dto.request.StudentController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLoginRequest {
    private String account;
    private String password;
}

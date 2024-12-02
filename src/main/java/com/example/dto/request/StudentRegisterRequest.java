package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterRequest {
    private String email;
    private String username;
    private String password;
    private String confirmPassword;
}
package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLoginRequest {
    private String account;
    private String password;
}

package com.example.dto.response.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLoginResponse {
    private String message;
    private Long id;
    private String token;
}

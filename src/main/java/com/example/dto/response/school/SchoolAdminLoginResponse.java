package com.example.dto.response.school;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolAdminLoginResponse {
    private String message;
    private Long id;
    private String token;
}

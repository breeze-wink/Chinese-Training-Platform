package com.example.dto.response.system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemAdminLoginResponse {
    private String message;
    private Long id;
    private String token;
}

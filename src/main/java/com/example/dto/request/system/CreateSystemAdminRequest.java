package com.example.dto.request.system;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateSystemAdminRequest {
    private String email;
    private String password;
    private String username;
    private String code;
}

package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchoolAdminLoginRequest {
    private String account;
    private String password;
}

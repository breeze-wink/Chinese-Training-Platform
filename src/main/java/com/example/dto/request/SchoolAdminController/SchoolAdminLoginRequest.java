package com.example.dto.request.SchoolAdminController;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchoolAdminLoginRequest {
    private String account;
    private String password;
}

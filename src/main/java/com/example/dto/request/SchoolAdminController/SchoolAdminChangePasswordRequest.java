package com.example.dto.request.SchoolAdminController;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolAdminChangePasswordRequest {
    private String password;
    private String newPassword;
}
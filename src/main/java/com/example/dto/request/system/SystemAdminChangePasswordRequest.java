package com.example.dto.request.system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemAdminChangePasswordRequest {
    private String password;
    private String newPassword;
}

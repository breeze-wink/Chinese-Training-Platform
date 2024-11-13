package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}

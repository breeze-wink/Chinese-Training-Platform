package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordResetRequest {
    private String type;
    private String email;
    private String code;
    private String password;
}

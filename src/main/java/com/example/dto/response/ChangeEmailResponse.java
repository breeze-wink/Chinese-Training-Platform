package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeEmailResponse {
    private String message;
    private String newEmail;
}

package com.example.dto.request.school;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateManagerRequest {
    private String email;
    private String password;
}

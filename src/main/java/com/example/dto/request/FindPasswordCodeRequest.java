package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FindPasswordCodeRequest {
    private String email;
    private String type;
}

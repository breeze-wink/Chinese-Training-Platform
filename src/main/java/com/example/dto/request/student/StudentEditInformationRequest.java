package com.example.dto.request.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEditInformationRequest {
    private String username;
    private String name;
    private int grade;
}

package com.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherCreateClassRequest {
    private String className;
    private String classDescription;
}
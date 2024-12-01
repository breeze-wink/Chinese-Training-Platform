package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeacherCreateClassResponse {
    private String message;
    private String classCode;
    private Long classId;
}

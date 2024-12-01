package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateClassRequest {
    private Long classId;
    private String className;
    private String classDescription;
}

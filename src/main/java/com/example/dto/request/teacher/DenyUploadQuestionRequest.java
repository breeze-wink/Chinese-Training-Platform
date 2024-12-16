package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DenyUploadQuestionRequest {
    private Long id;
    private String comment;
}

package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GeneratePaperWithTypeRequest {
    private List<Type> types;

    @Setter
    @Getter
    public static class Type {
        private String type;
        private Integer number;
    }
}

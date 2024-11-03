package com.example.model.classes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassGroup {
    private Long id;
    private Long classId;
    private String name;
    private String description;
}

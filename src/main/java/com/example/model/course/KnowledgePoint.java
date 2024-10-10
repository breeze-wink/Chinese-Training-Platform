package com.example.model.course;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class KnowledgePoint {

    private Long id;
    private String name;
    private String description;
    private Long courseStandardId; // 外键，关联CourseStandard表的id

}

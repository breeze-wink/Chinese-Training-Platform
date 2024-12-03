package com.example.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student extends BaseUser {
    private String name;
    private Integer grade;
    private Long schoolId;
}
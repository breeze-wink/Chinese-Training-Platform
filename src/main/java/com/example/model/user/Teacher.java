package com.example.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teacher extends BaseUser {
    public static final Integer TEACHER = 0;
    public static final Integer QUESTION_MANAGER = 1;
    private String name;
    private String phoneNumber;
    private Long schoolId;
    private Integer permission;
}
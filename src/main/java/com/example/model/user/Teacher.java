package com.example.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teacher extends BaseUser {
    private String name;
    private String phoneNumber;
    private Long schoolId;
    private Integer permission;
}
package com.example.model.user;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Student {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String name;
    private Integer grade;
    private Long schoolId;
}

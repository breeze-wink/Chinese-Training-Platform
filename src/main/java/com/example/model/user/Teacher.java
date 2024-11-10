package com.example.model.user;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Teacher {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Long schoolId; // 外键，关联School表的id

}
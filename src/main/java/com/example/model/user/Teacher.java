package com.example.model.user;

import lombok.Setter;
import lombok.Getter;
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
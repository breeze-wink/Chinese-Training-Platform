package com.example.model.user;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SchoolAdmin {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private Long schoolId;
}

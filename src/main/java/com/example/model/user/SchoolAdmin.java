package com.example.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchoolAdmin extends BaseUser {
    private String name;
    private Long schoolId;
}
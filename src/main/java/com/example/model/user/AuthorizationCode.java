package com.example.model.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AuthorizationCode {

    private String code;
    private Long schoolId; // 外键，关联School表的id
    private LocalDate createDate;

}
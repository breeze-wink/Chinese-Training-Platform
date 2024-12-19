package com.example.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SchoolAdmin extends BaseUser {
    private String name;
    private Long schoolId;

    public String info() {
        String ret = "(id:" + id;
        if (name != null && !name.isEmpty()) {
            ret += ", 姓名:" + name;
        }
        if (username != null && !username.isEmpty()) {
            ret += ", 昵称:" + username;
        }
        ret += ")";
        return ret;
    }
}
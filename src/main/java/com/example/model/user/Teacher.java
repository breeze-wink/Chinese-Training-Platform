package com.example.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teacher extends BaseUser {
    public static final Integer TEACHER = 0;
    public static final Integer Leader = 1;
    public static final Integer WORKING_STATUS = 1;
    public static final Integer STOP_STATUS = 0;
    private String name;
    private String phoneNumber;
    private Long schoolId;
    private Integer permission;
    private Integer status;

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
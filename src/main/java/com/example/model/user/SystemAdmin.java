package com.example.model.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemAdmin extends BaseUser {
    public String info() {
        String ret = "(id:" + id;
        if (username != null && !username.isEmpty()) {
            ret += ", 昵称:" + username;
        }
        ret += ")";
        return ret;
    }
}
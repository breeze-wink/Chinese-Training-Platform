package com.example.model.classes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassGroup {
    private Long id;
    private Long classId;
    private String name;
    private String description;

    public String info() {
        String ret = "(id:" + id;
        if (name != null && !name.isEmpty()) {
            ret += ", 名字:" + name;
        }
        ret += ")";
        return ret;
    }
}

package com.example.model.course;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class KnowledgePoint {

    private Long id;
    private String name;
    private String description;
    private String type;
    private Boolean isHidden;

    public String info() {
        String ret = "(id:" + id;
        if (name != null && !name.isEmpty()) {
            ret += ", 名字:" + name;
        }
        if (type != null && !type.isEmpty()) {
            ret += ", 类型:" + type;
        }
        ret += ")";
        return ret;
    }
}

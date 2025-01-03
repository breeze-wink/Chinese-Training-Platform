package com.example.model.classes;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinClass {
    private Long id;
    private Long classId;
    private Long studentId;

    public String info() {
        return "(id:" + id + ",classId:" + classId + ",studentId:" + studentId + ")";
    }
}

package com.example.model.course;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CourseStandard {

    private Long id;
    private String title;
    private byte[] content;
    private LocalDate executedDate;

    public String info() {
        return "(id:" + id + ", 标题:" + title + ", 执行日期:" + executedDate + ")";
    }
}

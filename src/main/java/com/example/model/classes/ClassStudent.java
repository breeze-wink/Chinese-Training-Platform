package com.example.model.classes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ClassStudent {
    private Long classId;
    private Long studentId;
    private LocalDateTime joinDate;

}

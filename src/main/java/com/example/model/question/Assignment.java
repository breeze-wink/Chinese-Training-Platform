package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Assignment {

    private Long id;
    private Long paperId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long creatorId; // 外键，关联Teacher表的id
    private String description;

    public String info() {
        return "(id:" + id + ", title:" + title + ")";
    }
}

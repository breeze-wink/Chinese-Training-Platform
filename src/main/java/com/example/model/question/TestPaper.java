package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class TestPaper {
    private Long id;
    private String name;
    private Long creatorId;
    private LocalDateTime createTime;
    private Double difficulty;
    private Integer totalScore;

    public String info() {
        return "(试卷id:" + id + ", 试卷名:" + name + ")";
    }
}

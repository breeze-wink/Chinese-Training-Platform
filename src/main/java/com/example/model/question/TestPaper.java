package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TestPaper {
    private Long id;
    private String name;
    private Long creatorId;
    private Date createTime;
    private Double difficulty;
    private Integer totalScore;
}

package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TestPaper {
    private Long id;
    private String name;
    private String description;
    private Long creatorId;
    private Date createTime;
}

package com.example.model.essay;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Essay {
    private Long id;
    private String title;
    private byte[] content;
    private Date submitDate;

    // Constructors
    public Essay() {}

    public Essay(Long id, String title, byte[] content, Date submitDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.submitDate = submitDate;
    }
}

package com.example.model.essay;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class Essay {
    private Long id;
    private String title;
    private byte[] content;
    private LocalDate submitDate;

    // Constructors
    public Essay() {}

    public Essay(Long id, String title, byte[] content, LocalDate submitDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.submitDate = submitDate;
    }
}

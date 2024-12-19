package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UploadQuestion implements Serializable {
    private Long id;
    private Long teacherId;
    private Long questionId;
    private String type;

    public String info() {
        return "(id:" + id + ", teacherId: " + teacherId + "questionId: " + questionId + ")";
    }
}

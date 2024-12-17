package com.example.dto.redis;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class SubQuestion implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private Long questionId;
    private String questionContent;
    private String type;
    private String questionOptions;
    private String questionAnswer;
    private String questionExplanation;
    private String knowledgePoint;
}

package com.example.model.view;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionUsageView {
    private Long questionId;
    private String type;   // 'small' 或 'big'
    private Boolean used;  // true 或 false
}
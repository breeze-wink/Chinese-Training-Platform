package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchQuestionsRequest {
    private String type; // "CHOICE" or "FILL_IN_BLANK", "SHORT_ANSWER", "ESSAY"
    private String knowledgeType;
    private Long knowledgeId;
    private String difficulty; // "容易" or "普通" or "困难" or '全部'
    private String mode; // "latest" or "mostUsed" or ''
    private String sortOrder;
    private Integer page;
    private Integer pageSize;
    private String search;
}

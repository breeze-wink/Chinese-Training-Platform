package com.example.model.question;

import lombok.Data;

@Data
public class QuestionBody {
    private Long id;          // 自动生成的ID
    private String body;      // 存储题目的正文，通常是富文本格式
    private String type;      // 用来标记题目类型（例如选择题、填空题等）
}
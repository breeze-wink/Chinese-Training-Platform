package com.example.model.question;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class QuestionBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    public static final Integer STATUS_NOT_ACCESS = 0;
    public static final Integer STATUS_ACCESS = 1;
    public static final Integer STATUS_DELETE = 2;

    private Long id;          // 自动生成的ID
    private String body;      // 存储题目的正文，通常是富文本格式
    private String type;      // 用来标记题目类型（例如选择题、填空题等）
    private Integer status;
}
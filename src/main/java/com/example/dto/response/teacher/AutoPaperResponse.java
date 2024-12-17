package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AutoPaperResponse {

    private String message;           // 消息
    private List<Question> questions; // 普通问题
    private List<BigQuestion> bigQuestions; // 后续大题
    private Question essay;

    @Getter
    @Setter
    public static class Question {
        private Long id;
        private String body;
        private String content;          // 问题内容
        private String answer;           // 答案
        private String explanation;      // 解释
        private List<String> options;    // 选项
        private String type;             // 题目类型
        private String knowledgePoint;   // 知识点
        private Double difficulty;
    }

    @Getter
    @Setter
    public static class BigQuestion {
        private Long id;
        private String body;                   // 题干内容
        private Double difficulty;
        private List<SubQuestion> subQuestions; // 子问题列表
    }
    @Getter
    @Setter
    public static class SubQuestion {
        private String content;          // 子问题内容
        private String answer;           // 答案
        private String explanation;      // 解释
        private List<String> options;    // 选项
        private String type;             // 题目类型
        private String knowledgePoint;   // 知识点
    }
}

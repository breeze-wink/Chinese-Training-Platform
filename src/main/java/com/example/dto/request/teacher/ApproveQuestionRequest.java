package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class ApproveQuestionRequest {
    private Long id;  // upload_question 的 ID
    private String body;  // 题干内容
    private String comment;  // 备注
    private List<QuestionInfo> questions;  // 题目列表

    @Setter
    @Getter
    public static class QuestionInfo {
        private String problem;  // 问题描述
        private List<String> choices;  // 选项列表，如果是选择题则提供选项，非选择题为空数组
        private String answer;  // 题目答案
        private String analysis;  // 题目解析
    }
}

package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetPaperDetailResponse {
    private String message;
    private Integer totalScore;
    private List<QuestionInfo> questions;

    @Setter
    @Getter
    public static class QuestionInfo {
        private String body; // 题干
        private Integer sequence;
        private String score;
        private String question; // 小题时不为空
        private String answer; // 小题时不为空
        private String explanation; // 小题时不为空
        private List<String> options; // 小题且为选择时不为空
        private String type; // 题目类型
        private String knowledge; // 知识点
        private List<SubQuestion> subQuestions; // 大题时不为空

        @Setter
        @Getter
        public static class SubQuestion {
            private String question; // 子问题内容
            private String answer; // 子问题答案
            private String explanation; // 子问题解析
            private List<String> options; // 子问题选项（如果是选择题）
            private String type; // 子问题类型
            private String knowledge; // 子问题的知识点
            private String score;
        }
    }
}

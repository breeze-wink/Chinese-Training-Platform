package com.example.dto.request.TeacherController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UploadQuestionRequest {
    private String questionType; // '单题', '文言文阅读', '记叙文阅读', '非连续性文本阅读', '古诗词曲鉴赏', '名著阅读'
    private List<QuestionInfo> questions;
    private String body;

    @Setter
    @Getter
    public static class QuestionInfo {
        private String type; // CHOICE, FILL_IN_BLANK, SHORT_ANSWER, ESSAY
        private String problem;
        private List<String> choices; //若不是选择题则为空
        private List<String> answer;
        private String analysis;
        private Long knowledgePointId;
    }

}

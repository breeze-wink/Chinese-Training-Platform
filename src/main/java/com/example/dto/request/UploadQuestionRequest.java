package com.example.dto.request;

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

    @Setter
    @Getter
    public static class QuestionInfo {
        private String body;
        private Type type; // CHOICE, FILL_IN_THE_BLANK, SHORT_ANSWER, ESSAY
        private String problem;
        private List<String> choices; //若不是选择题则为空
        private String answer;
        private String analysis;
        private Long knowledgePointId;
    }

    public enum Type {
        CHOICE("CHOICE"),
        FILL_IN_THE_BLANK("FILL_IN_THE_BLANK"),
        SHORT_ANSWER("SHORT_ANSWER"),
        ESSAY("ESSAY");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @JsonValue
        String getValue() {
            return value;
        }

        @JsonCreator
        public static Type fromValue(String value) {
            for (Type type : Type.values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("无法识别的题目类型: " + value);
        }
    }
}

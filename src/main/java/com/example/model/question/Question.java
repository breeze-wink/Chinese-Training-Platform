package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Question implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String content;
    private String type; // "CHOICE", "FILL_IN_BLANK", "SHORT_ANSWER"
    private String options; // 选项，JSON格式
    private String answer;
    private Long knowledgePointId; // 外键，关联KnowledgePoint表的id
    private Long creatorId; // 外键，关联Teacher表的id
    private Long bodyId;
    private Boolean isHidden;
}

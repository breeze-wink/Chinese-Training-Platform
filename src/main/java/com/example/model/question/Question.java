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
    public static final Integer STATUS_NOT_ACCESS = 0;
    public static final Integer STATUS_ACCESS = 1;
    public static final Integer STATUS_DELETE = 2;

    private Long id;
    private String content;
    private String type; // "CHOICE", "FILL_IN_BLANK", "SHORT_ANSWER"
    private String options; // 选项，JSON格式
    private String answer;
    private Long knowledgePointId; // 外键，关联KnowledgePoint表的id
    private Long creatorId; // 外键，关联Teacher表的id
    private Long bodyId;
    private Integer status;
}

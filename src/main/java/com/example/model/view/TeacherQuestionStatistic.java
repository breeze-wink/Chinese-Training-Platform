package com.example.model.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TeacherQuestionStatistic {
    private Long teacherId;           // 教师ID
    private LocalDateTime uploadTime; // 上传时间
    private Long questionId;          // 题目ID
    private String questionType;      // 题目类型（单题/组合体）
    private String status;            // 审核状态（通过/拒绝）
    private String comment;           // 审核备注
}
package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class GetUploadedQuestionResponse {
    private String message;
    private List<UploadQuestionInfo> uploadedQuestions;

    @Setter
    @Getter
    public static class UploadQuestionInfo {
        private Long questionId;        // 问题 ID
        private String type;            // 题目类型 (small or big)
        private String uploadTime;      // 上传时间 (string 格式)
        private String status;          // 状态 (未审核, 通过, 拒绝)
        private String comment;         // 备注
        private String executeTeacher;  // 审核老师名字
    }
}

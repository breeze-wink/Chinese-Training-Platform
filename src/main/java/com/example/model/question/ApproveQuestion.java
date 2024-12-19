package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApproveQuestion {
    public static String APPROVE = "approved";
    public static String REJECT = "rejected";
    private Long id;          // 主键
    private Long uploadId;    // 外键，引用上传题目的 ID
    private Long executeTeacherId;
    private String comment;   // 备注
    private String status;    // 审核状态：approved 或 rejected
}
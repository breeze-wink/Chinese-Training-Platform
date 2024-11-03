package com.example.model.question;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssignmentRecipient {
    private Long assignmentId;
    private String recipientType; // 值为 'CLASS'、'GROUP' 或 'STUDENT'
    private Long recipientId;
}

package com.example.service.question;

import com.example.model.question.AssignmentRecipient;

import java.util.List;

public interface AssignmentRecipientService {
    List<AssignmentRecipient> selectByRecipient(String type, Long recipientId);
}

package com.example.service.question.impl;

import com.example.mapper.question.AssignmentRecipientMapper;
import com.example.model.question.AssignmentRecipient;
import com.example.service.question.AssignmentRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentRecipientServiceImpl implements AssignmentRecipientService {

    private final AssignmentRecipientMapper assignmentRecipientMapper;
    @Autowired
    public AssignmentRecipientServiceImpl(AssignmentRecipientMapper assignmentRecipientMapper) {
        this.assignmentRecipientMapper = assignmentRecipientMapper;
    }

    @Override
    @Transactional
    public List<AssignmentRecipient> selectByRecipient(String type, Long recipientId) {
        return assignmentRecipientMapper.selectByRecipient(type, recipientId);
    }
}

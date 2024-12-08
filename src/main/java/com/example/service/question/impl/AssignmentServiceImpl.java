package com.example.service.question.impl;

import com.example.mapper.question.AssignmentMapper;
import com.example.model.question.Assignment;
import com.example.service.question.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentMapper assignmentMapper;
    @Autowired
    public AssignmentServiceImpl(AssignmentMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    @Transactional
    public Assignment selectById(Long id) {
        return assignmentMapper.selectById(id);
    }
}

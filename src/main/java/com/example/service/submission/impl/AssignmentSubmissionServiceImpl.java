package com.example.service.submission.impl;

import com.example.mapper.submission.AssignmentSubmissionMapper;
import com.example.model.submission.AssignmentSubmission;
import com.example.service.submission.AssignmentSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentSubmissionServiceImpl implements AssignmentSubmissionService {
    private final AssignmentSubmissionMapper assignmentSubmissionMapper;
    @Autowired
    public AssignmentSubmissionServiceImpl(AssignmentSubmissionMapper assignmentSubmissionMapper) {
        this.assignmentSubmissionMapper = assignmentSubmissionMapper;
    }

    @Override
    @Transactional
    public List<AssignmentSubmission> selectByStudentId(Long studentId) {
        return assignmentSubmissionMapper.selectByStudentId(studentId);
    }
}

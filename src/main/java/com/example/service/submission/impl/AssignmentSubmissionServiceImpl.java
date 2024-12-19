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

    @Override
    @Transactional
    public AssignmentSubmission selectByAssignmentIdAndStudentId(Long assignmentId, Long studentId) {
        return assignmentSubmissionMapper.selectByAssignmentIdAndStudentId(assignmentId, studentId);
    }

    @Override
    @Transactional
    public int insert(AssignmentSubmission submission) {
        return assignmentSubmissionMapper.insert(submission);
    }

    @Override
    @Transactional
    public AssignmentSubmission selectById(Long id) {
        return assignmentSubmissionMapper.selectById(id);
    }

    @Override
    @Transactional
    public int update(AssignmentSubmission submission) {
        return assignmentSubmissionMapper.update(submission);
    }
}

package com.example.service.submission;

import com.example.model.submission.AssignmentSubmission;

import java.util.List;

public interface AssignmentSubmissionService {
    List<AssignmentSubmission> selectByStudentId(Long studentId);
    AssignmentSubmission selectByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
    int insert(AssignmentSubmission submission);
    AssignmentSubmission selectById(Long id);
    int update(AssignmentSubmission submission);
}

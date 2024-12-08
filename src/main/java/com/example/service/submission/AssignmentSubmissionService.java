package com.example.service.submission;

import com.example.model.submission.AssignmentSubmission;

import java.util.List;

public interface AssignmentSubmissionService {
    List<AssignmentSubmission> selectByStudentId(Long studentId);
    AssignmentSubmission selectByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
}

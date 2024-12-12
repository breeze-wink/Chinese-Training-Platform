package com.example.service.submission;

import com.example.model.submission.SubmissionAnswer;

import java.util.List;

public interface SubmissionAnswerService {
    int insert(SubmissionAnswer answer);
    List<SubmissionAnswer> selectBySubmissionId(Long submissionId);
    SubmissionAnswer selectById(Long id);
    int update(SubmissionAnswer answer);
}

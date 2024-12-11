package com.example.service.submission.impl;

import com.example.mapper.submission.SubmissionAnswerMapper;
import com.example.model.submission.SubmissionAnswer;
import com.example.service.submission.SubmissionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubmissionAnswerServiceImpl implements SubmissionAnswerService {
    private final SubmissionAnswerMapper submissionAnswerMapper;
    @Autowired
    public SubmissionAnswerServiceImpl(SubmissionAnswerMapper submissionAnswerMapper) {
        this.submissionAnswerMapper = submissionAnswerMapper;
    }

    @Override
    @Transactional
    public int insert(SubmissionAnswer answer) {
        return submissionAnswerMapper.insert(answer);
    }

    @Override
    @Transactional
    public List<SubmissionAnswer> selectBySubmissionId(Long submissionId) {
        return submissionAnswerMapper.selectBySubmissionId(submissionId);
    }

    @Override
    @Transactional
    public SubmissionAnswer selectById(Long id) {
        return submissionAnswerMapper.selectById(id);
    }

    @Override
    @Transactional
    public int update(SubmissionAnswer answer) {
        return submissionAnswerMapper.update(answer);
    }
}

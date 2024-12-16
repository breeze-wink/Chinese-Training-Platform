package com.example.service.question.impl;

import com.example.mapper.question.ApproveQuestionMapper;
import com.example.model.question.ApproveQuestion;
import com.example.service.question.ApproveQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApproveQuestionServiceImpl implements ApproveQuestionService {

    @Autowired
    private ApproveQuestionMapper approveQuestionMapper;

    @Override
    @Transactional
    public int insert(ApproveQuestion approveQuestion) {
        return approveQuestionMapper.insert(approveQuestion);
    }

    @Override
    public ApproveQuestion selectById(Long id) {
        return approveQuestionMapper.selectById(id);
    }

    @Override
    public List<ApproveQuestion> selectByUploadId(Long uploadId) {
        return approveQuestionMapper.selectByUploadId(uploadId);
    }

    @Override
    @Transactional
    public int update(ApproveQuestion approveQuestion) {
        return approveQuestionMapper.update(approveQuestion);
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return approveQuestionMapper.delete(id);
    }
}
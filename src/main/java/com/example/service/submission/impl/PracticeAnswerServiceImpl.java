package com.example.service.submission.impl;

import com.example.mapper.submission.PracticeAnswerMapper;
import com.example.model.submission.PracticeAnswer;
import com.example.service.submission.PracticeAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PracticeAnswerServiceImpl implements PracticeAnswerService {
    private final PracticeAnswerMapper practiceAnswerMapper;
    @Autowired
    public PracticeAnswerServiceImpl(PracticeAnswerMapper practiceAnswerMapper) {
        this.practiceAnswerMapper = practiceAnswerMapper;
    }

    @Override
    @Transactional
    public int addPracticeAnswer(PracticeAnswer practiceAnswer) {
        return practiceAnswerMapper.insert(practiceAnswer);
    }

    @Override
    @Transactional
    public int deletePracticeAnswer(Long id) {
        return practiceAnswerMapper.delete(id);
    }

    @Override
    public int updatePracticeAnswer(PracticeAnswer practiceAnswer) {
        return practiceAnswerMapper.update(practiceAnswer);
    }

    @Override
    @Transactional
    public int deletePracticeAnswerByPracticeQuestionId(Long practiceQuestionId) {
        return practiceAnswerMapper.deletePracticeQuestionByPracticeId(practiceQuestionId);
    }

    @Override
    @Transactional
    public PracticeAnswer getPracticeAnswerById(Long id) {
        return practiceAnswerMapper.selectById(id);
    }

    @Override
    @Transactional
    public PracticeAnswer getPracticeAnswerByPracticeQuestionId(Long practiceQuestionId) {
        return practiceAnswerMapper.selectByPracticeQuestionId(practiceQuestionId);
    }
}

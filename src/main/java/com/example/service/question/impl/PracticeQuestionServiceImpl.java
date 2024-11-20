package com.example.service.question.impl;

import com.example.mapper.question.PracticeQuestionMapper;
import com.example.model.question.PracticeQuestion;
import com.example.service.question.PracticeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticeQuestionServiceImpl implements PracticeQuestionService {
    private final PracticeQuestionMapper practiceQuestionMapper;
    @Autowired
    public PracticeQuestionServiceImpl(PracticeQuestionMapper practiceQuestionMapper) {
        this.practiceQuestionMapper = practiceQuestionMapper;
    }
    @Override
    public int addPracticeQuestion(PracticeQuestion practiceQuestion) {
        return practiceQuestionMapper.insertPracticeQuestion(practiceQuestion);
    }

    @Override
    public int deletePracticeQuestion(Long id) {
        return practiceQuestionMapper.deletePracticeQuestion(id);
    }

    @Override
    public int deletePracticeQuestionByPracticeId(Long practiceId) {
        return practiceQuestionMapper.deletePracticeQuestionByPracticeId(practiceId);
    }

    @Override
    public PracticeQuestion getPracticeQuestionById(Long id) {
        return practiceQuestionMapper.selectPracticeQuestionById(id);
    }

    @Override
    public List<PracticeQuestion> getPracticeQuestionByPracticeId(Long practiceId) {
        return practiceQuestionMapper.selectPracticeQuestionsByPracticeId(practiceId);
    }
}

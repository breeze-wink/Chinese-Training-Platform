package com.example.service.question.impl;

import com.example.model.question.Practice;
import com.example.service.question.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.mapper.question.PracticeMapper;

import java.util.List;

@Service
public class PracticeServiceImpl implements PracticeService {
    private final PracticeMapper practiceMapper;
    @Autowired
    public PracticeServiceImpl(PracticeMapper practiceMapper) {
        this.practiceMapper = practiceMapper;
    }
    @Override
    @Transactional
    public int createPractice(Practice practice) {
        return practiceMapper.insert(practice);
    }

    @Override
    @Transactional
    public int deletePractice(Long id) {
        return practiceMapper.delete(id);
    }

    @Override
    @Transactional
    public int updatePractice(Practice practice) {
        return practiceMapper.update(practice);
    }

    @Override
    @Transactional
    public Practice getPracticeById(Long id) {
        return practiceMapper.selectById(id);
    }

    @Override
    @Transactional
    public List<Practice> getPracticesByStudentId(Long studentId) {
        return practiceMapper.selectByStudentId(studentId);
    }
}

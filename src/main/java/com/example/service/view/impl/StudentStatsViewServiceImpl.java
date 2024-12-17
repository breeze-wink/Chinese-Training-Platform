package com.example.service.view.impl;

import com.example.mapper.view.StudentStatsViewMapper;
import com.example.model.view.StudentStatsView;
import com.example.service.view.StudentStatsViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentStatsViewServiceImpl implements StudentStatsViewService {
    private final StudentStatsViewMapper studentStatsViewMapper;
    @Autowired
    public StudentStatsViewServiceImpl(StudentStatsViewMapper studentStatsViewMapper) {
        this.studentStatsViewMapper = studentStatsViewMapper;
    }

    @Override
    @Transactional
    public List<StudentStatsView> selectByClassId(Long classId) {
        return studentStatsViewMapper.selectByClassId(classId);
    }
}

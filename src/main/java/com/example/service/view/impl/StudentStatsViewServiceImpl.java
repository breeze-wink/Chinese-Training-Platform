package com.example.service.view.impl;

import com.example.mapper.view.StudentStatsViewMapper;
import com.example.service.view.StudentStatsViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentStatsViewServiceImpl implements StudentStatsViewService {
    private final StudentStatsViewMapper studentStatsViewMapper;
    @Autowired
    public StudentStatsViewServiceImpl(StudentStatsViewMapper studentStatsViewMapper) {
        this.studentStatsViewMapper = studentStatsViewMapper;
    }
}

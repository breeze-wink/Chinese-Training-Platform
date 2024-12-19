package com.example.service.user.impl;


import com.example.mapper.user.StatsStudentMapper;
import com.example.model.user.StatsStudent;
import com.example.service.user.StatsStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatsStudentServiceImpl implements StatsStudentService {
    private final StatsStudentMapper statsStudentMapper;
    @Autowired
    public StatsStudentServiceImpl(StatsStudentMapper statsStudentMapper) {
        this.statsStudentMapper = statsStudentMapper;
    }
    @Override
    @Transactional
    public int addStatsStudent(StatsStudent statsStudent) {
        return statsStudentMapper.insert(statsStudent);
    }

    @Override
    @Transactional
    public int updateStatsStudent(StatsStudent statsStudent) {
        return statsStudentMapper.update(statsStudent);
    }

    @Override
    @Transactional
    public List<StatsStudent> getStatsStudentByStudentId(Long studentId) {
        return statsStudentMapper.selectByStudentId(studentId);
    }

    @Override
    @Transactional
    public StatsStudent selectByStudentIdAndKnowledgePointId(Long studentId, Long knowledgePointId) {
        return statsStudentMapper.selectByStudentIdAndKnowledgePointId(studentId, knowledgePointId);
    }
}

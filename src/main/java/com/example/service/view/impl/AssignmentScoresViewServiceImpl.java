package com.example.service.view.impl;

import com.example.mapper.view.AssignmentScoresViewMapper;
import com.example.model.view.AssignmentIdStudentIdScore;
import com.example.model.view.AssignmentScoresView;
import com.example.service.view.AssignmentScoresViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentScoresViewServiceImpl implements AssignmentScoresViewService {
    private final AssignmentScoresViewMapper assignmentScoresViewMapper;
    @Autowired
    public AssignmentScoresViewServiceImpl(AssignmentScoresViewMapper assignmentScoresViewMapper) {
        this.assignmentScoresViewMapper = assignmentScoresViewMapper;
    }

    @Override
    @Transactional
    public List<AssignmentScoresView> selectAvgScoresByClassId(Long classId) {
        return assignmentScoresViewMapper.selectAvgScoresByClassId(classId);
    }

    @Override
    @Transactional
    public List<AssignmentIdStudentIdScore> selectScoresByStudentId(Long studentId) {
        return assignmentScoresViewMapper.selectScoresByStudentId(studentId);
    }

    @Override
    @Transactional
    public List<AssignmentScoresView> selectByGroupId(Long groupId) {
        return assignmentScoresViewMapper.selectByGroupId(groupId);
    }
}

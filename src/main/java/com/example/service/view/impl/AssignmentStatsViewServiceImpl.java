package com.example.service.view.impl;

import com.example.mapper.view.AssignmentStatsViewMapper;
import com.example.model.view.AssignmentStatsView;
import com.example.service.view.AssignmentStatsViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignmentStatsViewServiceImpl implements AssignmentStatsViewService {
    private final AssignmentStatsViewMapper assignmentStatsViewMapper;
    @Autowired
    public AssignmentStatsViewServiceImpl(AssignmentStatsViewMapper assignmentStatsViewMapper) {
        this.assignmentStatsViewMapper = assignmentStatsViewMapper;
    }

    @Override
    @Transactional
    public AssignmentStatsView selectBySubmissionAnswerId(Long submissionAnswerId) {
        return assignmentStatsViewMapper.selectBySubmissionAnswerId(submissionAnswerId);
    }
}

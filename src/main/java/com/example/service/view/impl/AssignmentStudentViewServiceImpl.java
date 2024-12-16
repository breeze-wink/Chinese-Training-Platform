package com.example.service.view.impl;

import com.example.mapper.view.AssignmentStudentViewMapper;
import com.example.model.view.AssignmentStudentView;
import com.example.service.view.AssignmentStudentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssignmentStudentViewServiceImpl implements AssignmentStudentViewService {
    private final AssignmentStudentViewMapper assignmentStudentMapper;
    @Autowired
    public AssignmentStudentViewServiceImpl(AssignmentStudentViewMapper assignmentStudentMapper) {
        this.assignmentStudentMapper = assignmentStudentMapper;
    }

    @Override
    @Transactional
    public AssignmentStudentView selectByAssignmentIdAndStudentId(Long assignmentId, Long studentId) {
        return assignmentStudentMapper.selectByAssignmentIdAndStudentId(assignmentId, studentId);
    }

    @Override
    @Transactional
    public List<AssignmentStudentView> selectByAssignmentId(Long assignmentId) {
        return assignmentStudentMapper.selectByAssignmentId(assignmentId);
    }

    @Override
    @Transactional
    public List<AssignmentStudentView> selectByStudentId(Long studentId) {
        return assignmentStudentMapper.selectByStudentId(studentId);
    }

    @Override
    @Transactional
    public List<AssignmentStudentView> selectByTeacherId(Long teacherId) {
        return assignmentStudentMapper.selectByTeacherId(teacherId);
    }
}

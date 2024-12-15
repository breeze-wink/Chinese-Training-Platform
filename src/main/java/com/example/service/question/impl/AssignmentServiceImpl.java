package com.example.service.question.impl;

import com.example.mapper.question.AssignmentMapper;
import com.example.model.question.Assignment;
import com.example.service.question.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentMapper assignmentMapper;
    @Autowired
    public AssignmentServiceImpl(AssignmentMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Assignment selectById(Long id) {
        return assignmentMapper.selectById(id);
    }

    @Override
    @Transactional
    public int createAssignment(Assignment assignment) {
        // 使用插入方法，返回受影响的行数
        return assignmentMapper.insert(assignment);
    }

    @Override
    @Transactional
    public int deleteAssignment(Long id) {
        // 删除指定 ID 的作业
        return assignmentMapper.delete(id);
    }

    @Override

    @Transactional
    public int updateAssignment(Assignment assignment) {
        // 更新作业信息
        return assignmentMapper.update(assignment);
    }

    @Override

    @Transactional(readOnly = true)
    public List<Assignment> getAllAssignments() {
        // 获取所有作业
        return assignmentMapper.selectAll();
    }
}

package com.example.service.classes.impl;

import com.example.mapper.classes.GroupStudentMapper;
import com.example.model.classes.GroupStudent;
import com.example.service.classes.GroupStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupStudentServiceImpl implements GroupStudentService {

    @Autowired
    private GroupStudentMapper groupStudentMapper;

    @Override
    public int addGroupStudent(GroupStudent groupStudent) {
        return groupStudentMapper.insert(groupStudent);
    }

    @Override
    public int removeGroupStudent(Long groupId, Long studentId) {
        return groupStudentMapper.delete(groupId, studentId);
    }

    @Override
    public GroupStudent getGroupStudentByIds(Long groupId, Long studentId) {
        return groupStudentMapper.selectByIds(groupId, studentId);
    }

    @Override
    public List<GroupStudent> getAllGroupStudents() {
        return groupStudentMapper.selectAll();
    }

    @Override
    public List<GroupStudent> getGroupStudentsByGroupId(Long groupId) {
        return groupStudentMapper.selectByGroupId(groupId);
    }

    @Override
    public List<GroupStudent> getGroupStudentsByStudentId(Long studentId) {
        return groupStudentMapper.selectByStudentId(studentId);
    }

    @Override
    public int removeAllStudentsFromGroup(Long groupId) {
        return groupStudentMapper.removeGroup(groupId);
    }
}

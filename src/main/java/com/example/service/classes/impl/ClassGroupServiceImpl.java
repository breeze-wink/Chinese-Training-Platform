package com.example.service.classes.impl;

import com.example.mapper.classes.ClassGroupMapper;
import com.example.mapper.classes.GroupStudentMapper;
import com.example.model.classes.ClassGroup;
import com.example.model.classes.GroupStudent;
import com.example.service.classes.ClassGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClassGroupServiceImpl implements ClassGroupService {
    private final ClassGroupMapper classGroupMapper;
    private final GroupStudentMapper groupStudentMapper;
    @Autowired
    public ClassGroupServiceImpl(ClassGroupMapper classGroupMapper, GroupStudentMapper groupStudentMapper) {
        this.classGroupMapper = classGroupMapper;
        this.groupStudentMapper = groupStudentMapper;
    }
    @Override
    @Transactional
    public int createGroup(Long classId, String groupName, String groupDescription){
        ClassGroup classGroup = new ClassGroup();
        classGroup.setClassId(classId);
        classGroup.setName(groupName);
        classGroup.setDescription(groupDescription);
        return classGroupMapper.insert(classGroup);
    }
    @Override
    @Transactional
    public int removeGroup(Long groupId){
        groupStudentMapper.removeGroup(groupId);
        return classGroupMapper.delete(groupId);
    }
    @Override
    @Transactional
    public int addStudentToGroup(Long groupId, Long studentId){
        GroupStudent groupStudent = new GroupStudent();
        groupStudent.setGroupId(groupId);
        groupStudent.setStudentId(studentId);
        return groupStudentMapper.insert(groupStudent);
    }
    @Override
    @Transactional
    public int removeStudentFromGroup(Long groupId, Long studentId){
        return groupStudentMapper.delete(groupId, studentId);
    }
}

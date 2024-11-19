package com.example.service.classes.impl;

import com.example.mapper.classes.ClassStudentMapper;
import com.example.model.classes.ClassStudent;
import com.example.service.classes.ClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassStudentServiceImpl implements ClassStudentService {
    private final ClassStudentMapper classStudentMapper;
    @Autowired
    public ClassStudentServiceImpl(ClassStudentMapper classStudentMapper) {
        this.classStudentMapper = classStudentMapper;
    }

    @Override
    @Transactional
    public int addClassStudent(ClassStudent classStudent) {
        return classStudentMapper.insert(classStudent);
    }

    @Override
    @Transactional
    public int removeClassStudent(Long classId, Long studentId) {
        return classStudentMapper.delete(classId, studentId);
    }

    @Override
    @Transactional
    public List<ClassStudent> getClassStudentByStudentId(Long studentId) {
        return classStudentMapper.selectByStudentId(studentId);
    }

    @Override
    @Transactional
    public ClassStudent selectByClassIdAndStudentId(Long classId, Long studentId) {
        return classStudentMapper.select(classId, studentId);
    }

    @Override
    @Transactional
    public List<ClassStudent> getClassStudentsByStudentId(Long studentId) {
        return classStudentMapper.selectByStudentId(studentId);
    }

    @Override
    @Transactional
    public List<ClassStudent> getClassStudentsByClassId(Long classId) {
        return classStudentMapper.selectByClassId(classId);
    }

}

package com.example.service.classes.impl;

import com.example.mapper.classes.JoinClassMapper;
import com.example.model.classes.JoinClass;
import com.example.service.classes.JoinClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JoinClassServiceImpl implements JoinClassService {
    private final JoinClassMapper joinClassMapper;
    @Autowired
    public JoinClassServiceImpl(JoinClassMapper joinClassMapper
                                ){
        this.joinClassMapper = joinClassMapper;
    }

    @Override
    @Transactional
    public int addJoinClass(JoinClass joinClass) {
        return joinClassMapper.insert(joinClass);
    }

    @Override
    @Transactional
    public int removeJoinClassByStudentId(Long studentId) {
        return joinClassMapper.deleteByStudentId(studentId);
    }

    @Override
    @Transactional
    public int removeJoinClassByClassId(Long classId) {
        return joinClassMapper.deleteByClassId(classId);
    }

    @Override
    @Transactional
    public JoinClass selectJoinClassByStudentIdAndClassId(Long studentId, Long classId) {
        return joinClassMapper.selectJoinClassByStudentIdAndClassId(studentId, classId);
    }

    @Override
    @Transactional
    public List<JoinClass> selectJoinClassByStudentId(Long studentId) {
        return joinClassMapper.selectJoinClassByStudentId(studentId);
    }

    @Override
    @Transactional
    public List<JoinClass> selectJoinClassByClassId(Long classId) {
        return joinClassMapper.selectJoinClassByClassId(classId);
    }

    @Override
    @Transactional
    public JoinClass selectJoinClassById(Long id) {
        return joinClassMapper.selectById(id);
    }
}

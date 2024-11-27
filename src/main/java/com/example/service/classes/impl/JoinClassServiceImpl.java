package com.example.service.classes.impl;

import com.example.mapper.classes.JoinClassMapper;
import com.example.model.classes.JoinClass;
import com.example.service.classes.JoinClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinClassServiceImpl implements JoinClassService {
    private final JoinClassMapper joinClassMapper;
    @Autowired
    public JoinClassServiceImpl(JoinClassMapper joinClassMapper
                                ){
        this.joinClassMapper = joinClassMapper;
    }

    @Override
    public int addJoinClass(JoinClass joinClass) {
        return joinClassMapper.insert(joinClass);
    }

    @Override
    public int removeJoinClassByStudentId(Long studentId) {
        return joinClassMapper.deleteByStudentId(studentId);
    }

    @Override
    public int removeJoinClassByClassId(Long classId) {
        return joinClassMapper.deleteByClassId(classId);
    }

    @Override
    public JoinClass selectJoinClassByStudentIdAndClassId(Long studentId, Long classId) {
        return joinClassMapper.selectJoinClassByStudentIdAndClassId(studentId, classId);
    }
}

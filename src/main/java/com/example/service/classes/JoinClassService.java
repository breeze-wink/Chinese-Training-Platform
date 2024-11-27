package com.example.service.classes;

import com.example.model.classes.JoinClass;

import java.util.List;

public interface JoinClassService {
    int addJoinClass(JoinClass joinClass);
    int removeJoinClassByStudentId(Long studentId);
    int removeJoinClassByClassId(Long classId);
    JoinClass selectJoinClassByStudentIdAndClassId(Long studentId, Long classId);
    List<JoinClass> selectJoinClassByStudentId(Long studentId);
    List<JoinClass> selectJoinClassByClassId(Long classId);
    JoinClass selectJoinClassById(Long id);
}

package com.example.service.classes;

import com.example.model.classes.JoinClass;

public interface JoinClassService {
    int addJoinClass(JoinClass joinClass);
    int removeJoinClassByStudentId(Long studentId);
    int removeJoinClassByClassId(Long classId);
    JoinClass selectJoinClassByStudentIdAndClassId(Long studentId, Long classId);
}

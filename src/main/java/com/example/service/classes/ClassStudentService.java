package com.example.service.classes;

import com.example.model.classes.ClassStudent;

import java.util.List;

public interface ClassStudentService {
    int addClassStudent(ClassStudent classStudent);
    int removeClassStudent(Long classId, Long studentId);
    List<ClassStudent> getClassStudentByStudentId(Long studentId);
    ClassStudent selectByClassIdAndStudentId(Long classId, Long studentId);

    List<ClassStudent> getClassStudentsByStudentId(Long studentId);
    List<ClassStudent> getClassStudentsByClassId(Long classId);
}

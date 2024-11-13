package com.example.service.classes;

import com.example.model.classes.Clazz;

import java.util.List;

public interface ClassService {
    int createClass(String className, String classDescription, Long creatorId);
    int addClass(Clazz clazz);
    int removeClass(Long classId);
    int updateClass(Clazz clazz);
    int joinClass(String inviteCode, Long studentId);
    int removeStudentFromClass(Long classId, Long studentId);
    Clazz getClassById(Long classId);
    List<Clazz> getAllClasses();
    Clazz getClassByInviteCode(String inviteCode);
}

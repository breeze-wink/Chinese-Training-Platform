package com.example.service.classes;

public interface ClassService {
    int createClass(String className, String classDescription, Long creatorId);
    int removeClass(Long classId);
    int joinClass(String inviteCode, Long studentId);
    int removeStudentFromClass(Long classId, Long studentId);
}

package com.example.service.classes;

public interface ClassGroupService {
    int createGroup(Long classId, String groupName, String groupDescription);
    int removeGroup(Long groupId);
    int addStudentToGroup(Long groupId, Long studentId);
    int removeStudentFromGroup(Long groupId, Long studentId);
}

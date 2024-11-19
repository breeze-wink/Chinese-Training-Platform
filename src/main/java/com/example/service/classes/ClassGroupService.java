package com.example.service.classes;

import com.example.model.classes.ClassGroup;

import java.util.List;

public interface ClassGroupService {
    int addClassGroup(ClassGroup classGroup);
    int updateClassGroup(ClassGroup classGroup);
    ClassGroup createGroup(Long classId, String groupName, String groupDescription);
    int removeGroup(Long groupId);
    int addStudentToGroup(Long groupId, Long studentId);
    int removeStudentFromGroup(Long groupId, Long studentId);
    List<ClassGroup> getGroupsByClassId(Long classId);
    ClassGroup getGroupById(Long groupId);
    List<ClassGroup> getAllGroups();
}

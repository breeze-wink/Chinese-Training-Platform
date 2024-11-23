package com.example.service.classes;

import com.example.model.classes.GroupStudent;

import java.util.List;

public interface GroupStudentService {

    int addGroupStudent(GroupStudent groupStudent);

    int removeGroupStudent(Long groupId, Long studentId);

    GroupStudent getGroupStudentByIds(Long groupId, Long studentId);

    List<GroupStudent> getAllGroupStudents();

    List<GroupStudent> getGroupStudentsByGroupId(Long groupId);

    List<GroupStudent> getGroupStudentsByStudentId(Long studentId);

    int removeAllStudentsFromGroup(Long groupId);
}

package com.example.service.user;

import com.example.model.user.Teacher;

import java.util.List;

public interface TeacherService {
    int addTeacher(Teacher teacher);
    int removeTeacher(Long id);

    int updateTeacher(Teacher teacher);
    Teacher getTeacherById(Long id);
    List<Teacher> getAllTeachers();

    Teacher authenticate(String email, String password);

    boolean existTeacher(String email);
    boolean existUsername(String username);

    Long getTeacherSchoolId(Long teacherId);
    List<Teacher> getTeachersBySchoolId(Long schoolId);
}

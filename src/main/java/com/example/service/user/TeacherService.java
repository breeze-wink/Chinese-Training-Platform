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

    boolean existTeacher(Teacher teacher);
    boolean existUsername(String username);
    List<Teacher> getTeachersBySchoolId(Long schoolId);

    Teacher getTeacherByUsername(String username);
}

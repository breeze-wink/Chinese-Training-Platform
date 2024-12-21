package com.example.service.user;

import com.example.model.user.Teacher;

import java.util.List;

public interface TeacherService {
    void addTeacher(Teacher teacher);
    int removeTeacher(Long id);

    void updateTeacher(Teacher teacher);
    Teacher getTeacherById(Long id);
    List<Teacher> getAllTeachers();

    Teacher authenticate(String email, String password);

    boolean existTeacher(Teacher teacher);
    boolean existUsername(String username);
    List<Teacher> getTeachersBySchoolId(Long schoolId);

    Teacher getTeacherByUsername(String username);

    String getTeacherNameById(Long teacherId);
    boolean ManagerEmailExist(String email);

    void updatePassword(Teacher teacher);

    Teacher selectByEmail(String email);
}

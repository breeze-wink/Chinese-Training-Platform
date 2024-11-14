package com.example.service.user;

import com.example.model.user.Student;

import java.util.List;

public interface StudentService {
    int addStudent(Student student);
    int removeStudent(Long id);
    int updateStudent(Student student);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    Student authenticate(String account, String password);
    boolean existStudent(String email);
    boolean existStudentUsername(String username);
    List<Student> getStudentsBySchoolId(Long schoolId);
}

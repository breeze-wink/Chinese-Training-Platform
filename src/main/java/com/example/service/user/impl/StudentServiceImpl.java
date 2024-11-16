package com.example.service.user.impl;

import com.example.mapper.user.StudentMapper;
import com.example.model.user.SchoolAdmin;
import com.example.model.user.Student;
import com.example.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;
    @Autowired
    public StudentServiceImpl (StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    @Transactional
    public int addStudent(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    @Transactional
    public int removeStudent(Long id) {
        return studentMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateStudent(Student student) {
        return studentMapper.update(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.selectAll();
    }

    @Override
    public Student authenticate(String account, String password) {
        Student student = studentMapper.findByAccountOrEmail(account);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        return null;
    }

    @Override
    public boolean existStudent(String email) {
        return studentMapper.findByEmail(email) != null;
    }

    @Override
    public boolean existStudentUsername(String username) {
        return studentMapper.findByUsername(username) != null;
    }

    @Override
    public List<Student> getStudentsBySchoolId(Long schoolId) {
        return studentMapper.selectBySchoolId(schoolId);
    }
}

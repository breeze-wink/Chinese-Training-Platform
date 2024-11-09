package com.example.service.user.impl;

import com.example.mapper.user.StudentMapper;
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
}

package com.example.service.user.impl;

import com.example.mapper.user.TeacherMapper;
import com.example.model.user.Teacher;
import com.example.service.user.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    @Transactional
    public int addTeacher(Teacher teacher) {
        return teacherMapper.insert(teacher);
    }

    @Override
    @Transactional
    public int removeTeacher(Long id) {
        return teacherMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateTeacher(Teacher teacher) {
        return teacherMapper.update(teacher);
    }

    @Override
    @Transactional
    public Teacher getTeacherById(Long id) {
        return teacherMapper.selectById(id);
    }

    @Override
    @Transactional
    public List<Teacher> getAllTeachers() {
        return teacherMapper.selectAll();
    }

    @Override
    public Teacher authenticate(String email, String password) {
        List<Teacher> teachers = teacherMapper.findByAccount(email);

        for (Teacher teacher : teachers) {
            if (teacher.getPassword().equals(password)) {
                return teacher;
            }
        }
        return null;
    }

    @Override
    public boolean existTeacher(String email) {
        return teacherMapper.findByEmail(email) != null;
    }

    @Override
    public boolean existUsername(String username) {
        return teacherMapper.findByUsername(username) != null;
    }


    @Override
    public List<Teacher> getTeachersBySchoolId(Long schoolId) {
        return teacherMapper.selectBySchoolId(schoolId);
    }

    @Override
    public Teacher getTeacherByUsername(String username) {
        return teacherMapper.findByUsername(username);
    }
}

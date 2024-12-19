package com.example.service.user.impl;

import com.example.mapper.user.TeacherMapper;
import com.example.model.user.Teacher;
import com.example.service.user.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherServiceImpl(TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }

    @Override
    @Transactional
    public void addTeacher(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    @Override
    @Transactional
    public int removeTeacher(Long id) {
        return teacherMapper.delete(id);
    }

    @Override
    @Transactional
    public void updateTeacher(Teacher teacher) {
        teacherMapper.update(teacher);
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
    public boolean existTeacher(Teacher teacher) {
        Teacher checkTeacher = teacherMapper.findByEmail(teacher.getEmail());
        return checkTeacher != null && Objects.equals(checkTeacher.getPermission(), teacher.getPermission());
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

    @Override
    public String getTeacherNameById(Long teacherId) {
        Teacher teacher =  teacherMapper.selectById(teacherId);
        if (Objects.equals(teacher.getStatus(), Teacher.WORKING_STATUS)) {
            return teacher.getName();
        }
        return teacher.getName() + "(已离职)";
    }

    @Override
    @Transactional
    public Boolean emailExist(String email) {
        Teacher teacher = teacherMapper.emailExist(email);
        return teacher != null;
    }

}

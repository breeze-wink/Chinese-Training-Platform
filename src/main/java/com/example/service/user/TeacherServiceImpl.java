package com.example.service.user;

import com.example.mapper.user.TeacherMapper;
import com.example.model.user.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{
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
    @Transactional
    public Long getTeacherSchoolId(Long teacherId) {
        Teacher teacher = getTeacherById(teacherId);
        return teacher.getSchoolId();
    }
}

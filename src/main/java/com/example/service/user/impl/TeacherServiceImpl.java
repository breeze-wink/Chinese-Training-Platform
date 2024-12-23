package com.example.service.user.impl;

import com.example.mapper.classes.ClassMapper;
import com.example.mapper.question.TestPaperMapper;
import com.example.mapper.user.TeacherMapper;
import com.example.model.user.Teacher;
import com.example.service.user.TeacherService;
import com.example.service.utils.PasswordEncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherMapper teacherMapper;
    private final PasswordEncodeService passwordEncodeService;
    private final ClassMapper classMapper;
    private final TestPaperMapper testPaperMapper;

    @Autowired
    public TeacherServiceImpl(TeacherMapper teacherMapper,
                              PasswordEncodeService passwordEncodeService,
                              ClassMapper classMapper,
                              TestPaperMapper testPaperMapper
                              ) {
        this.teacherMapper = teacherMapper;
        this.passwordEncodeService = passwordEncodeService;
        this.classMapper = classMapper;
        this.testPaperMapper = testPaperMapper;
    }

    @Override
    @Transactional
    public void addTeacher(Teacher teacher) {
        teacher.setPassword(passwordEncodeService.encode(teacher.getPassword()));
        teacherMapper.insert(teacher);
    }

    @Override
    @Transactional
    public int removeTeacher(Long id) {
        testPaperMapper.deleteByCreatorId(id);
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
            if (passwordEncodeService.matches(password, teacher.getPassword())) {
                return teacher;
            }
        }
        return null;
    }

    @Override
    public boolean existTeacher(Teacher teacher) {
        Teacher checkTeacher = teacherMapper.findByEmail(teacher.getEmail());
        return checkTeacher != null;
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
    public boolean ManagerEmailExist(String email) {
        Teacher teacher = teacherMapper.selectByEmail(email);
        return teacher != null && Objects.equals(teacher.getPermission(), Teacher.Leader);
    }

    @Override
    public void updatePassword(Teacher teacher) {
        teacher.setPassword(passwordEncodeService.encode(teacher.getPassword()));
        teacherMapper.update(teacher);
    }

    @Override
    @Transactional
    public Teacher selectByEmail(String email) {
        return teacherMapper.selectByEmail(email);
    }

    @Override
    public boolean checkPermissionCorrect(Long id, Integer permission) {
        return Objects.equals(teacherMapper.selectPermissionById(id), permission);
    }

}

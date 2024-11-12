package com.example.service.classes.impl;

import com.example.mapper.classes.ClassGroupMapper;
import com.example.mapper.classes.ClassMapper;
import com.example.mapper.classes.ClassStudentMapper;
import com.example.mapper.user.TeacherMapper;
import com.example.model.classes.Class;
import com.example.model.classes.ClassGroup;
import com.example.model.classes.ClassStudent;
import com.example.service.classes.ClassGroupService;
import com.example.service.classes.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class ClassServiceImpl implements ClassService {
    private final ClassMapper classMapper;
    private final ClassStudentMapper classStudentMapper;
    private final ClassGroupMapper classGroupMapper;
    private final TeacherMapper teacherMapper;
    @Autowired
    private ClassGroupService classGroupService;
    @Autowired
    public ClassServiceImpl(ClassMapper classMapper, ClassStudentMapper classStudentMapper, TeacherMapper teacherMapper, ClassGroupService classGroupService, ClassGroupMapper classGroupMapper) {
        this.classMapper = classMapper;
        this.classStudentMapper = classStudentMapper;
        this.teacherMapper = teacherMapper;
        this.classGroupService = classGroupService;
        this.classGroupMapper = classGroupMapper;
    }

    @Override
    @Transactional
    public int createClass(String className, String classDescription, Long creatorId) {
        Random random = new Random();
        String [] charsToCode= {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String inviteCode = charsToCode[random.nextInt(36)] + charsToCode[random.nextInt(36)] + charsToCode[random.nextInt(36)] + charsToCode[random.nextInt(36)] + charsToCode[random.nextInt(36)];
        while(classMapper.inviteCodeCheck(inviteCode) != null){
            inviteCode = charsToCode[random.nextInt(36)] + charsToCode[random.nextInt(36)] + charsToCode[random.nextInt(36)] + charsToCode[random.nextInt(36)] + charsToCode[random.nextInt(36)];
        }
        Class clazz = new Class();
        clazz.setName(className);
        clazz.setDescription(classDescription);
        clazz.setInviteCode(inviteCode);
        clazz.setCreatorId(creatorId);
        clazz.setSchoolId(teacherMapper.selectById(creatorId).getSchoolId());
        return classMapper.insert(clazz);
    }

    @Override
    @Transactional
    public int removeClass(Long classId) {
        List<ClassGroup> classGroups = classGroupMapper.selectByClassId(classId);
        for(ClassGroup classGroup : classGroups){
            classGroupService.removeGroup(classGroup.getId());
        }
        classStudentMapper.removeClass(classId);
        return classMapper.delete(classId);
    }

    @Override
    @Transactional
    public int joinClass(String inviteCode, Long studentId){
        ClassStudent classStudent = new ClassStudent();
        classStudent.setClassId(classMapper.selectIdByInviteCode(inviteCode));
        classStudent.setStudentId(studentId);
        Date now = new Date();
        now.setTime(now.getTime() + 28800000);
        classStudent.setJoinDate(now);
        return classStudentMapper.insert(classStudent);
    }

    @Override
    @Transactional
    public int removeStudentFromClass(Long classId, Long studentId){
        List<ClassGroup> classGroups = classGroupMapper.selectByClassId(classId);
        for(ClassGroup classGroup : classGroups){
            classGroupService.removeStudentFromGroup(classGroup.getId(), studentId);
        }
        return classStudentMapper.delete(classId, studentId);
    }
}

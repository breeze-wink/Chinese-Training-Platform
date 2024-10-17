package com.example.service.classes;
import com.example.mapper.user.TeacherMapper;
import com.example.mapper.classes.ClassStudentMapper;
import com.example.model.classes.Class;
import java.util.Date;
import com.example.mapper.classes.ClassMapper;
import com.example.model.classes.ClassStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;


@Service
public class ClassServiceImpl implements ClassService{
    private final ClassMapper classMapper;
    private final ClassStudentMapper classStudentMapper;
    private final TeacherMapper teacherMapper;
    @Autowired
    public ClassServiceImpl(ClassMapper classMapper, ClassStudentMapper classStudentMapper, TeacherMapper teacherMapper) {
        this.classMapper = classMapper;
        this.classStudentMapper = classStudentMapper;
        this.teacherMapper = teacherMapper;
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
        //Teacher teacher = teacherMapper.selectById(creatorId);
        Class clazz = new Class();
        clazz.setName(className);
        clazz.setDescription(classDescription);
        clazz.setInviteCode(inviteCode);
        clazz.setCreatorId(creatorId);
        clazz.setSchoolId(1L);
        return classMapper.insert(clazz);
    }

    @Override
    public int removeClass(Long classId) {
        classStudentMapper.removeClass(classId);
        return classMapper.delete(classId);
    }

    @Override
    public int joinClass(String inviteCode, Long studentId){
        ClassStudent classStudent = new ClassStudent();
        classStudent.setClassId(classStudentMapper.selectIdByInviteCode(inviteCode));
        classStudent.setStudentId(studentId);
        classStudent.setJoinDate(new Date());
        return classStudentMapper.insert(classStudent);
    }

    @Override
    public int removeStudent(Long classId, Long studentId){
        return classStudentMapper.delete(classId, studentId);
    }
}

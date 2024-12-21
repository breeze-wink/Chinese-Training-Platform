package com.example.service.user.impl;
import com.example.model.user.BaseUser;
import com.example.service.user.*;
import com.example.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserService {

    // 注入具体的服务
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SchoolAdminService schoolAdminService;
    private final SystemAdminService systemAdminService;

    public CustomUserService(StudentService studentService, TeacherService teacherService, SchoolAdminService schoolAdminService, SystemAdminService systemAdminService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.schoolAdminService = schoolAdminService;
        this.systemAdminService = systemAdminService;
    }

    @Override
    public BaseUser loadUserByUsernameAndClassName(Long id, String className) {
        // 根据用户名和类名获取不同类型的用户
        if ("Student".equals(className)) {
            return studentService.getStudentById(id);
        } else if ("Teacher".equals(className)) {
            return teacherService.getTeacherById(id);
        } else if ("SchoolAdmin".equals(className)) {
            return schoolAdminService.getSchoolAdminById(id);
        } else if ("SystemAdmin".equals(className)) {
            return systemAdminService.getSystemAdminById(id);
        } else {
            // 如果类名不匹配任何已知类型，返回null或抛出异常
            return null;
        }
    }
}
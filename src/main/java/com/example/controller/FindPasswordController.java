package com.example.controller;

import com.example.dto.request.FindPasswordCodeRequest;
import com.example.dto.request.FindPasswordResetRequest;
import com.example.dto.response.Message;
import com.example.model.user.SchoolAdmin;
import com.example.model.user.Student;
import com.example.model.user.SystemAdmin;
import com.example.model.user.Teacher;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.StudentService;
import com.example.service.user.SystemAdminService;
import com.example.service.user.TeacherService;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.user.impl.SystemAdminServiceImpl;
import com.example.service.user.impl.TeacherServiceImpl;
import com.example.service.utils.EmailCodeService;
import com.example.service.utils.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/find-password")
public class FindPasswordController {
    private final EmailCodeService emailCodeService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final SchoolAdminService schoolAdminService;
    private final SystemAdminService systemAdminService;
    private final EmailService emailService;
    @Autowired
    public FindPasswordController(EmailCodeService emailCodeService,
                                  StudentServiceImpl studentService,
                                  TeacherServiceImpl teacherService,
                                  SchoolAdminServiceImpl schoolAdminService,
                                  SystemAdminServiceImpl systemAdminService,
                                  EmailService emailService
                                  ) {
        this.emailCodeService = emailCodeService;
        this.studentService = studentService;
        this.emailService = emailService;
        this.teacherService = teacherService;
        this.schoolAdminService = schoolAdminService;
        this.systemAdminService = systemAdminService;
    }

    @PostMapping("/send-code")
    public ResponseEntity<Message> sendCode(@RequestBody FindPasswordCodeRequest request) throws MessagingException {
        Message response = new Message();
        String type = request.getType();
        if(Objects.equals(type, "student")){
            Student student = studentService.selectByEmail(request.getEmail());
            if(student == null){
                response.setMessage("账号不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        else if(Objects.equals(type, "teacher")){
            Teacher teacher = teacherService.selectByEmail(request.getEmail());
            if(teacher == null){
                response.setMessage("账号不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        else if(Objects.equals(type, "schoolAdmin")){
            SchoolAdmin schoolAdmin = schoolAdminService.selectByEmail(request.getEmail());
            if(schoolAdmin == null){
                response.setMessage("账号不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        else if(Objects.equals(type, "systemAdmin")){
            SystemAdmin systemAdmin = systemAdminService.selectByEmail(request.getEmail());
            if(systemAdmin == null){
                response.setMessage("账号不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        String verificationCode = emailService.sendEmail(request.getEmail());
        emailCodeService.setFindPasswordCode(type, request.getEmail(), verificationCode);
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Message> resetPassword(@RequestBody FindPasswordResetRequest request){
        Message response = new Message();
        String type = request.getType();
        String code = emailCodeService.getFindPasswordCode(type, request.getEmail());
        if(!Objects.equals(code, request.getCode())){
            response.setMessage("验证码错误或已失效");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(Objects.equals(type, "student")){
            Student student = studentService.selectByEmail(request.getEmail());
            student.setPassword(request.getPassword());
            studentService.updatePassword(student);
        }
        else if(Objects.equals(type, "teacher")){
            Teacher teacher = teacherService.selectByEmail(request.getEmail());
            teacher.setPassword(request.getPassword());
            teacherService.updatePassword(teacher);
        }
        else if(Objects.equals(type, "schoolAdmin")){
            SchoolAdmin schoolAdmin = schoolAdminService.selectByEmail(request.getEmail());
            schoolAdmin.setPassword(request.getPassword());
            schoolAdminService.updatePassword(schoolAdmin);
        }
        else if(Objects.equals(type, "systemAdmin")){
            SystemAdmin systemAdmin = systemAdminService.selectByEmail(request.getEmail());
            systemAdmin.setPassword(request.getPassword());
            systemAdminService.updatePassword(systemAdmin);
        }
        else{
            response.setMessage("类型错误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setMessage("密码重置成功");
        return ResponseEntity.ok(response);
    }
}

package com.example.controller;


import com.example.dto.request.student.*;
import com.example.dto.response.*;
import com.example.dto.response.student.StudentChangeEmailResponse;
import com.example.dto.response.student.StudentChangeEmailVerificationResponse;
import com.example.dto.response.student.StudentEditInformationResponse;
import com.example.dto.response.student.StudentJoinClassResponse;
import com.example.model.classes.ClassStudent;
import com.example.model.classes.Clazz;
import com.example.model.classes.JoinClass;
import com.example.model.user.Student;
import com.example.service.classes.ClassService;
import com.example.service.classes.ClassStudentService;
import com.example.service.classes.JoinClassService;
import com.example.service.user.SchoolService;
import com.example.service.user.StudentService;
import com.example.service.utils.EmailCodeService;
import com.example.service.utils.EmailService;
import com.example.service.utils.PasswordEncodeService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/student")
public class StudentInfoController {
    private static final Logger logger = LoggerFactory.getLogger(StudentInfoController.class);
    private static final Logger operationLogger = LoggerFactory.getLogger("operations.student");
    private final StudentService studentService;

    private final PasswordEncodeService passwordEncodeService;
    private final EmailService emailService;
    private final ClassStudentService classStudentService;
    private final ClassService classService;
    private final SchoolService schoolService;
    private final JoinClassService joinClassService;
    private final EmailCodeService emailCodeService;

    @Autowired
    public StudentInfoController(StudentService studentService,
                                 EmailService emailService,
                                 ClassStudentService classStudentService,
                                 ClassService classService,
                                 SchoolService schoolService,
                                 JoinClassService joinClassService,
                                 EmailCodeService emailCodeService,
                                 PasswordEncodeService passwordEncodeService
                                 ) {
        this.studentService = studentService;
        this.emailService = emailService;
        this.classStudentService = classStudentService;
        this.classService = classService;
        this.schoolService = schoolService;
        this.joinClassService = joinClassService;
        this.emailCodeService = emailCodeService;
        this.passwordEncodeService = passwordEncodeService;
    }
    @PostMapping("/{id}/edit-information")
    public ResponseEntity<StudentEditInformationResponse> studentEditInformation(@PathVariable Long id, @RequestBody StudentEditInformationRequest request) {
        try {
            StudentEditInformationResponse response = new StudentEditInformationResponse();
            Student student = studentService.getStudentById(id);
            if (student == null) {
                response.setMessage("用户未找到");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if(!request.getUsername().isEmpty()){
                if (studentService.existStudentUsername(request.getUsername()) && !student.getUsername().equals(request.getUsername())) {
                    response.setMessage("用户名已存在");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                student.setUsername(request.getUsername());
            }
            if(!request.getName().isEmpty()){
                student.setName(request.getName());
            }
            student.setGrade(request.getGrade());
            studentService.updateStudent(student);
            response.setMessage("个人信息修改成功");
            StudentEditInformationResponse.InfoData data = new StudentEditInformationResponse.InfoData();
            data.setUsername(student.getUsername());
            data.setName(student.getName());
            data.setGrade(student.getGrade());
            response.setData(data);
            operationLogger.info("学生 {} 修改个人信息", student.info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生修改个人信息失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/change-email/send-code")
    public ResponseEntity<StudentChangeEmailVerificationResponse> studentChangeEmailVerification(@PathVariable Long id, @RequestBody StudentChangeEmailVerificationRequest request) throws MessagingException {
        try {
            StudentChangeEmailVerificationResponse response = new StudentChangeEmailVerificationResponse();
            String email = request.getEmail();
            if (studentService.existStudent(email)) {
                response.setMessage("邮箱已注册");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            String verificationCode = emailService.sendEmail(email);
            emailCodeService.setCode("student", email, verificationCode);
            response.setMessage("验证码已发送");
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生发送邮箱验证码出现问题 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/{id}/change-email")
    public ResponseEntity<StudentChangeEmailResponse> studentChangeEmail(@PathVariable Long id, @RequestBody StudentChangeEmailRequest request) {
        try {
            StudentChangeEmailResponse response = new StudentChangeEmailResponse();
            String code = emailCodeService.getCode("student", request.getEmail());
            if(!Objects.equals(code, request.getVerificationCode())){
                response.setMessage("验证码错误或已失效");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            Student student = studentService.getStudentById(id);
            student.setEmail(request.getEmail());
            int result = studentService.updateStudent(student);
            if (result == 0) {
                response.setMessage("邮箱更换失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            response.setMessage("邮箱更换成功");
            StudentChangeEmailResponse.InfoData data = new StudentChangeEmailResponse.InfoData();
            data.setEmail(request.getEmail());
            response.setData(data);
            operationLogger.info("学生 {} 更改邮箱", student.info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生更换邮箱失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Message> studentChangePassword(@PathVariable Long id, @RequestBody StudentChangePasswordRequest request) {
        try {
            Student student = studentService.getStudentById(id);
            Message response = new Message();
            if(!passwordEncodeService.matches(request.getOldPassword(), student.getPassword())){
                response.setMessage("旧密码错误");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            student.setPassword(request.getNewPassword());
            studentService.updatePassword(student);
            response.setMessage("密码修改成功");
            operationLogger.info("学生 {} 修改密码", student.info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生修改密码失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}/account-deactivation")
    public ResponseEntity<Message> studentAccountDeactivation(@PathVariable Long id) {
        try {
            Message response = new Message();
            List<ClassStudent> classStudents = classStudentService.getClassStudentsByStudentId(id);
            for (ClassStudent classStudent : classStudents) {
                classService.removeStudentFromClass(classStudent.getClassId(), id);
            }
            studentService.removeStudent(id);
            response.setMessage("账户注销成功");
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生注销账户失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/join-class")
    public ResponseEntity<StudentJoinClassResponse> studentJoinClass(@PathVariable Long id, @RequestBody StudentJoinClassRequest request) {
        try {
            StudentJoinClassResponse response = new StudentJoinClassResponse();
            String inviteCode = request.getInviteCode();
            Clazz clazz = classService.getClassByInviteCode(inviteCode);
            if(clazz == null){
                response.setMessage("邀请码错误");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            ClassStudent classStudent = classStudentService.getClassStudentByStudentId(id);
            if(classStudent != null){
                response.setMessage("请勿重复加入班级");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            ClassStudent newClassStudent = new ClassStudent();
            newClassStudent.setClassId(clazz.getId());
            newClassStudent.setStudentId(id);
            LocalDateTime now = LocalDateTime.now();
            newClassStudent.setJoinDate(now);
            classStudentService.addClassStudent(newClassStudent);
            Student student = studentService.getStudentById(id);
            student.setSchoolId(clazz.getSchoolId());
            studentService.updateStudent(student);
            response.setMessage("加入成功");
            response.setClassName(clazz.getName());
            response.setSchoolName(schoolService.getSchoolById(clazz.getSchoolId()).getName());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生加入班级失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

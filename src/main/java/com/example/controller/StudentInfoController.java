package com.example.controller;


import com.example.dto.request.*;
import com.example.dto.response.*;
import com.example.model.classes.ClassStudent;
import com.example.model.classes.Clazz;
import com.example.model.user.Student;
import com.example.service.classes.ClassService;
import com.example.service.classes.ClassStudentService;
import com.example.service.user.SchoolService;
import com.example.service.user.StudentService;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.utils.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/student")
public class StudentInfoController {
    private final StudentService studentService;
    private final EmailService emailService;
    private final ClassStudentService classStudentService;
    private final ClassService classService;
    private final SchoolService schoolService;

    @Autowired
    public StudentInfoController(StudentService studentService,
                                 EmailService emailService,
                                 ClassStudentService classStudentService,
                                 ClassService classService,
                                 SchoolService schoolService) {
        this.studentService = studentService;
        this.emailService = emailService;
        this.classStudentService = classStudentService;
        this.classService = classService;
        this.schoolService = schoolService;
    }
    @PostMapping("/{id}/editInformation")
    public ResponseEntity<StudentEditInformationResponse> studentEditInformation(@PathVariable Long id, @RequestBody StudentEditInformationRequest request) {
        StudentEditInformationResponse response = new StudentEditInformationResponse();
        Student student = studentService.getStudentById(id);
        if (student == null) {
            response.setMessage("用户未找到");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (studentService.existStudentUsername(request.getUsername()) && !student.getUsername().equals(request.getUsername())) {
            response.setMessage("用户名已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        student.setUsername(request.getUsername());
        student.setName(request.getName());
        student.setGrade(request.getGrade());
        studentService.updateStudent(student);
        response.setMessage("个人信息修改成功");
        StudentEditInformationResponse.InfoData data = new StudentEditInformationResponse.InfoData();
        data.setUsername(student.getUsername());
        data.setName(student.getName());
        data.setGrade(student.getGrade());
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/change-email/send-verification")
    public ResponseEntity<StudentChangeEmailVerificationResponse> studentChangeEmailVerification(@PathVariable Long id, @RequestBody StudentChangeEmailVerificationRequest request) throws MessagingException {
        String email = request.getEmail();
        StudentChangeEmailVerificationResponse response = new StudentChangeEmailVerificationResponse();
        if (studentService.existStudent(email)) {
            response.setMessage("邮箱已注册");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String verificationCode = emailService.sendEmail(email);
        StudentChangeEmailVerificationResponse.InfoData data = new StudentChangeEmailVerificationResponse.InfoData();
        data.setVerificationCode(verificationCode);
        response.setData(data);
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}/changeEmail")
    public ResponseEntity<StudentChangeEmailResponse> studentChangeEmail(@PathVariable Long id, @RequestBody StudentChangeEmailRequest request) {
        StudentChangeEmailResponse response = new StudentChangeEmailResponse();
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
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Message> studentChangePassword(@PathVariable Long id, @RequestBody StudentChangePasswordRequest request) {
        Student student = studentService.getStudentById(id);
        Message response = new Message();
        if(!Objects.equals(student.getPassword(), request.getOldPassword())){
            response.setMessage("旧密码错误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        student.setPassword(request.getNewPassword());
        studentService.updateStudent(student);
        response.setMessage("密码修改成功");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/account-deactivation")
    public ResponseEntity<Message> studentAccountDeactivation(@PathVariable Long id) {
        Message response = new Message();
        List<ClassStudent> classStudents = classStudentService.getClassStudentByStudentId(id);
        for (ClassStudent classStudent : classStudents) {
            classService.removeStudentFromClass(classStudent.getClassId(), id);
        }
        studentService.removeStudent(id);
        response.setMessage("账户注销成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/join-class")
    public ResponseEntity<StudentJoinClassResponse> studentJoinClass(@PathVariable Long id, @RequestBody StudentJoinClassRequest request) {
        StudentJoinClassResponse response = new StudentJoinClassResponse();
        StudentJoinClassResponse.InfoData data = new StudentJoinClassResponse.InfoData();
        String inviteCode = request.getInviteCode();
        Clazz clazz = classService.getClassByInviteCode(inviteCode);
        if (clazz == null) {
            response.setMessage("班级码无效");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        ClassStudent classStudent = classStudentService.selectByClassIdAndStudentId(clazz.getId(), id);
        if(classStudent != null){
            response.setMessage("不允许重复加入班级");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        int result = classService.joinClass(inviteCode, id);
        if (result == 0) {
            response.setMessage("加入班级失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        data.setClassName(clazz.getName());
        data.setSchoolName(schoolService.getSchoolById(clazz.getSchoolId()).getName());
        response.setData(data);
        response.setMessage("加入班级成功");
        return ResponseEntity.ok(response);
    }
}

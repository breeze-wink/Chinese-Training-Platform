package com.example.controller;

import com.example.dto.request.*;
import com.example.dto.response.*;
import com.example.model.user.AuthorizationCode;
import com.example.model.user.Student;
import com.example.model.user.Teacher;
import com.example.service.user.SchoolService;
import com.example.service.user.StudentService;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.utils.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Student")
public class StudentController {
    private final StudentService studentService;
    private final EmailService emailService;
    private final SchoolService schoolService;
    @Autowired
    public StudentController(StudentServiceImpl studentService, EmailService emailService, SchoolService schoolService) {
        this.studentService = studentService;
        this.emailService = emailService;
        this.schoolService = schoolService;
    }
    @PostMapping("/login")
    public ResponseEntity<StudentLoginResponse> login(@RequestBody StudentLoginRequest request) {
        String account = request.getAccount();
        String password = request.getPassword();

        Student student = studentService.authenticate(account, password);

        StudentLoginResponse response = new StudentLoginResponse();
        if (student != null) {
            response.setMessage("success");
            response.setId(student.getId());
            return ResponseEntity.ok(response);
        }
        else {
            response.setMessage("用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @PostMapping("/send-verification")
    public ResponseEntity<StudentVerifyResponse> sendVerificationCode(@RequestBody StudentVerifyRequest request) throws MessagingException {
        String email = request.getEmail();
        StudentVerifyResponse response = new StudentVerifyResponse();
        if (studentService.existStudent(email)) {
            response.setMessage("邮箱已注册");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String verificationCode = emailService.sendEmail(email);
        response.setVerificationCode(verificationCode);
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<StudentRegisterResponse> studentRegister(@RequestBody StudentRegisterRequest request) {
        StudentRegisterResponse response = new StudentRegisterResponse();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();
        if(studentService.existStudentUsername(request.getUsername())){
            response.setMessage("用户名已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (!password.equals(confirmPassword)) {
            response.setMessage("密码输入不一致");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Student student = new Student();
        student.setEmail(request.getEmail());
        student.setUsername(request.getUsername());
        student.setPassword(request.getPassword());

        studentService.addStudent(student);
        response.setId(student.getId());
        response.setMessage("注册成功");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentInfoResponse> getStudentInfo(@PathVariable Long id) {
        StudentInfoResponse response = new StudentInfoResponse();
        Student student = studentService.getStudentById(id);
        if (student == null) {
            response.setMessage("用户未找到");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Success");
        StudentInfoResponse.InfoData data = new StudentInfoResponse.InfoData();
        data.setUsername(student.getUsername());
        data.setEmail(student.getEmail());
        data.setName(student.getName());
        data.setGrade(student.getGrade());
        data.setSchoolName(null);
        if (student.getSchoolId() != null) {
            data.setSchoolName(schoolService.getSchoolById(student.getSchoolId()).getName());
        }
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}

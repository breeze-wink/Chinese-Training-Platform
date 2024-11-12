package com.example.controller;

import com.example.dto.request.TeacherLoginRequest;
import com.example.dto.request.TeacherRegisterRequest;
import com.example.dto.request.TeacherVerifyRequest;
import com.example.dto.response.TeacherInfoResponse;
import com.example.dto.response.TeacherLoginResponse;
import com.example.dto.response.TeacherRegisterResponse;
import com.example.dto.response.TeacherVerifyResponse;
import com.example.model.user.AuthorizationCode;
import com.example.model.user.Teacher;
import com.example.service.user.AuthorizationCodeService;
import com.example.service.user.SchoolService;
import com.example.service.user.TeacherService;
import com.example.service.user.impl.AuthorizationCodeServiceImpl;
import com.example.service.user.impl.TeacherServiceImpl;
import com.example.service.utils.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final AuthorizationCodeService authorizationCodeService;

    private final SchoolService schoolService;
    private final EmailService emailService;

    @Autowired
    public TeacherController(TeacherServiceImpl teacherService,
                             AuthorizationCodeServiceImpl authorizationCodeService,
                             EmailService emailService,
                             SchoolService schoolService) {
        this.teacherService = teacherService;
        this.authorizationCodeService = authorizationCodeService;
        this.emailService = emailService;
        this.schoolService = schoolService;
    }

    @PostMapping("/login")
    public ResponseEntity<TeacherLoginResponse> login(@RequestBody TeacherLoginRequest request) {
        String account = request.getAccount();
        String password = request.getPassword();

        Teacher teacher = teacherService.authenticate(account, password);

        TeacherLoginResponse response = new TeacherLoginResponse();
        if (teacher != null) {
            response.setMessage("success");
            response.setId(teacher.getId());
            return ResponseEntity.ok(response);
        }
        else {
            response.setMessage("用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @PostMapping("/send-verification")
    public ResponseEntity<TeacherVerifyResponse> sendVerificationCode(@RequestBody TeacherVerifyRequest request) throws MessagingException {
        String email = request.getEmail();
        String code = request.getAuthorizationCode();

        TeacherVerifyResponse response = new TeacherVerifyResponse();
        AuthorizationCode authorizationCode = authorizationCodeService.getAuthorizationCodeByCode(code);
        if (authorizationCode == null) {
            response.setMessage("授权码不可用");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (teacherService.existTeacher(email)) {
            response.setMessage("邮箱已注册");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String verificationCode = emailService.sendEmail(email);
        response.setVerificationCode(verificationCode);
        response.setSchoolId(authorizationCode.getSchoolId());
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<TeacherRegisterResponse> register(@RequestBody TeacherRegisterRequest request) {
        TeacherRegisterResponse response = new TeacherRegisterResponse();
        String email = request.getEmail();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            response.setMessage("密码输入不一致");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Teacher teacher = new Teacher();
        teacher.setEmail(request.getEmail());
        teacher.setPassword(request.getPassword());
        teacher.setSchoolId(request.getSchoolId());

        teacherService.addTeacher(teacher);
        response.setId(teacher.getId());
        response.setMessage("注册成功");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TeacherInfoResponse> getTeacherInfo(@PathVariable Long id) {
        TeacherInfoResponse response = new TeacherInfoResponse();
        Teacher teacher = teacherService.getTeacherById(id);

        if (teacher == null) {
            response.setMessage("用户未找到");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Success");
        TeacherInfoResponse.InfoData data = new TeacherInfoResponse.InfoData();
        data.setName(teacher.getName());
        data.setPhoneNumber(teacher.getPhoneNumber());
        data.setEmail(teacher.getEmail());
        data.setSchoolName(schoolService.getSchoolById(teacher.getSchoolId()).getName());
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}

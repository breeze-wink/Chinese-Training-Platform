package com.example.controller;

import com.example.dto.request.*;
import com.example.dto.request.teacher.*;
import com.example.dto.response.*;
import com.example.dto.response.teacher.TeacherInfoResponse;
import com.example.dto.response.teacher.TeacherLoginResponse;
import com.example.dto.response.teacher.TeacherRegisterResponse;
import com.example.dto.response.teacher.TeacherVerifyResponse;
import com.example.model.user.AuthorizationCode;
import com.example.model.user.BaseUser;
import com.example.model.user.Teacher;
import com.example.service.user.AuthorizationCodeService;
import com.example.service.user.SchoolService;
import com.example.service.user.TeacherService;
import com.example.service.user.impl.AuthorizationCodeServiceImpl;
import com.example.service.user.impl.TeacherServiceImpl;
import com.example.service.utils.EmailService;
import com.example.util.JwtTokenUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
public class TeacherManagementController {
    private final TeacherService teacherService;
    private final AuthorizationCodeService authorizationCodeService;

    private final SchoolService schoolService;
    private final EmailService emailService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public TeacherManagementController(TeacherServiceImpl teacherService,
                                       AuthorizationCodeServiceImpl authorizationCodeService,
                                       EmailService emailService,
                                       SchoolService schoolService,
                                       JwtTokenUtil jwtTokenUtil) {
        this.teacherService = teacherService;
        this.authorizationCodeService = authorizationCodeService;
        this.emailService = emailService;
        this.schoolService = schoolService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<TeacherLoginResponse> login(@RequestBody TeacherLoginRequest request) {
        String account = request.getAccount();
        String password = request.getPassword();

        Teacher teacher = teacherService.authenticate(account, password);

        TeacherLoginResponse response = new TeacherLoginResponse();
        if (teacher != null) {
            String jwt = jwtTokenUtil.generateToken(teacher);
            response.setToken(jwt);
            response.setMessage("success");
            response.setPermission(teacher.getPermission());
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
        Teacher teacher = new Teacher();
        teacher.setEmail(email);
        teacher.setPermission(0);
        if (teacherService.existTeacher(teacher)) {
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
        teacher.setPermission(Teacher.TEACHER);
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
        response.setMessage("success");
        TeacherInfoResponse.InfoData data = new TeacherInfoResponse.InfoData();
        data.setName(teacher.getName());
        data.setUsername(teacher.getUsername());
        data.setPhoneNumber(teacher.getPhoneNumber());
        data.setEmail(teacher.getEmail());
        data.setSchoolName(schoolService.getSchoolById(teacher.getSchoolId()).getName());
        response.setData(data);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}/update-username")
    public ResponseEntity<Message> updateUsername(@PathVariable Long id, @RequestBody UpdateUsernameRequest request) {
        Message response = new Message();
        String newName = request.getUsername();
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            response.setMessage("Id错误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (teacherService.existUsername(newName)) {
            response.setMessage("用户名已存在，修改失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        //开始执行
        teacher.setUsername(newName);
        teacherService.updateTeacher(teacher);

        response.setMessage("用户名修改成功");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}/update-phoneNumber")
    public ResponseEntity<Message> updatePhoneNumber(@PathVariable Long id, @RequestBody TeacherUpdatePhoneNumberRequest request) {
        Message response = new Message();
        String newNumber = request.getPhoneNumber();
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            response.setMessage("Id错误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        //开始执行
        teacher.setPhoneNumber(newNumber);
        teacherService.updateTeacher(teacher);

        response.setMessage("手机号修改成功");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/update-name")
    public ResponseEntity<Message> updateName(@PathVariable Long id, @RequestBody UpdateNameRequest request) {
        Message response = new Message();
        String newNumber = request.getName();
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            response.setMessage("Id错误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        //开始执行
        teacher.setName(request.getName());
        teacherService.updateTeacher(teacher);

        response.setMessage("姓名修改成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Message> changePassword(@PathVariable Long id, @RequestBody TeacherChangePasswordRequest request) {
        Message response = new Message();
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher == null) {
            response.setMessage("用户不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (!teacher.getPassword().equals(request.getPassword())) {
            response.setMessage("旧密码错误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        teacher.setPassword(request.getNewPassword());
        teacherService.updateTeacher(teacher);
        response.setMessage("密码修改成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/send-email-code")
    public ResponseEntity<SendEmailCodeResponse> sendEmailCode(@RequestParam String email) throws MessagingException {
        SendEmailCodeResponse response = new SendEmailCodeResponse();
        if (teacherService.emailExist(email)) {
            response.setMessage("邮箱已注册");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String code = emailService.sendEmail(email);
        response.setCode(code);
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/change-email")
    public ResponseEntity<ChangeEmailResponse> changeEmail(@AuthenticationPrincipal BaseUser user, @RequestParam String newEmail) {
        ChangeEmailResponse response = new ChangeEmailResponse();
        try {
            Teacher teacher = teacherService.getTeacherById(user.getId());
            teacher.setEmail(newEmail);
            teacherService.updateTeacher(teacher);
            response.setMessage("邮箱更换成功");
            response.setNewEmail(newEmail);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage("邮箱更换失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<Message> deleteAccount(@AuthenticationPrincipal BaseUser user) {
        Message response = new Message();
        try {
            Teacher teacher = teacherService.getTeacherById(user.getId());
            teacherService.removeTeacher(teacher.getId());
            response.setMessage("删除成功");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage("删除失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

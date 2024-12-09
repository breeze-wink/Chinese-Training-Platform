package com.example.controller;

import com.example.dto.request.school.SchoolAdminBindEmailRequest;
import com.example.dto.request.school.SchoolAdminLoginRequest;
import com.example.dto.response.school.SchoolEmailVerifyResponse;
import com.example.dto.request.school.SchoolAdminChangePasswordRequest;
import com.example.dto.request.UpdateNameRequest;
import com.example.dto.request.UpdateUsernameRequest;
import com.example.dto.response.Message;
import com.example.dto.response.school.SchoolAdminInfoResponse;
import com.example.dto.response.school.SchoolAdminLoginResponse;
import com.example.model.user.AuthorizationCode;
import com.example.model.user.School;
import com.example.model.user.SchoolAdmin;
import com.example.service.user.AuthorizationCodeService;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.SchoolService;

import com.example.service.utils.EmailService;
import com.example.util.JwtTokenUtil;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school-admin")
public class SchoolAdminManagementController {
    private final SchoolAdminService schoolAdminService;
    private final SchoolService schoolService;
    private final AuthorizationCodeService authorizationCodeService;

    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;

    @Autowired
    public SchoolAdminManagementController(SchoolAdminService schoolAdminService,
                                           SchoolService schoolService,
                                           AuthorizationCodeService authorizationCodeService,
                                           EmailService emailService,
                                           JwtTokenUtil jwtTokenUtil) {
        this.schoolAdminService = schoolAdminService;
        this.schoolService = schoolService;
        this.authorizationCodeService = authorizationCodeService;
        this.emailService = emailService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<SchoolAdminLoginResponse> login(@RequestBody SchoolAdminLoginRequest request) {
        String account = request.getAccount();
        String password = request.getPassword();

        SchoolAdmin admin = schoolAdminService.authenticate(account, password);

        if (admin != null) {
            SchoolAdminLoginResponse response = new SchoolAdminLoginResponse();
            String jwt = jwtTokenUtil.generateToken(admin);
            response.setToken(jwt);
            response.setMessage("success");
            response.setId(admin.getId());
            return ResponseEntity.ok(response);
        }
        else {
            SchoolAdminLoginResponse response = new SchoolAdminLoginResponse();
            response.setMessage("用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<SchoolAdminInfoResponse> getSchoolAdminInfo(@PathVariable Long id) {
        SchoolAdmin admin = schoolAdminService.getSchoolAdminById(id);
        SchoolAdminInfoResponse response = new SchoolAdminInfoResponse();
        if (admin != null) {
            AuthorizationCode code = authorizationCodeService.getAuthorizationCodeBySchoolId(admin.getSchoolId());
            response.setMessage("success");
            School school = schoolService.getSchoolById(admin.getSchoolId());
            SchoolAdminInfoResponse.InfoData data = new SchoolAdminInfoResponse.InfoData();
            data.setUsername(admin.getUsername());
            data.setEmail(admin.getEmail());
            data.setName(admin.getName());
            data.setSchoolName(school.getName());
            if(code == null){
                data.setAuthorizationCode("无");
            }
            else{
                data.setAuthorizationCode(code.getCode());
                data.setCreateDate(code.getCreateDate().toString());
            }
            response.setData(data);

            return ResponseEntity.ok(response);
        } else {
            response.setMessage("用户未找到");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Message> changePassword(@PathVariable Long id, @RequestBody SchoolAdminChangePasswordRequest request) {
        Message response = new Message();
        SchoolAdmin admin = schoolAdminService.getSchoolAdminById(id);
        if(admin == null) {
            response.setMessage("用户未找到");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(!admin.getPassword().equals(request.getPassword())){
            response.setMessage("旧密码错误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        admin.setPassword(request.getNewPassword());
        schoolAdminService.updateSchoolAdmin(admin);
        response.setMessage("密码修改成功");
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/update-username")
    public ResponseEntity<Message> updateUserName(@PathVariable Long id, @RequestBody UpdateUsernameRequest request) {
        String username = request.getUsername();

        try {
            if (schoolAdminService.checkExistUsername(username)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("用户名已存在"));
            }
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            schoolAdmin.setUsername(username);
            schoolAdminService.updateSchoolAdmin(schoolAdmin);
            return ResponseEntity.ok(new Message("修改成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("修改错误" + e.getMessage()));
        }
    }

    @PutMapping("/{id}/update-name")
    public ResponseEntity<Message> updateName(@PathVariable Long id, @RequestBody UpdateNameRequest request) {
        String name = request.getName();

        try {

            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            schoolAdmin.setName(name);
            schoolAdminService.updateSchoolAdmin(schoolAdmin);
            return ResponseEntity.ok(new Message("修改成功"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("修改错误" + e.getMessage()));
        }
    }

    @PostMapping("/{id}/send-verification-code")
    public ResponseEntity<SchoolEmailVerifyResponse> sendVerificationCode(@PathVariable Long id, @RequestBody SchoolAdminBindEmailRequest request) throws MessagingException {
        String email = request.getEmail();

        SchoolEmailVerifyResponse response = new SchoolEmailVerifyResponse();

        String verificationCode = emailService.sendEmail(email);
        response.setVerificationCode(verificationCode);
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/bind-email")
    public ResponseEntity<Message> bindEmail(@PathVariable Long id, @RequestBody SchoolAdminBindEmailRequest request) {
        String email = request.getEmail();

        try {
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            schoolAdmin.setEmail(email);
            schoolAdminService.updateSchoolAdmin(schoolAdmin);
            return ResponseEntity.ok(new Message("成功"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message(e.getMessage()));
        }

    }

}

package com.example.controller;

import com.example.dto.request.school.SchoolAdminBindEmailRequest;
import com.example.dto.request.school.SchoolAdminLoginRequest;
import com.example.dto.response.ChangeEmailResponse;
import com.example.dto.response.SendEmailCodeResponse;
import com.example.dto.response.school.SchoolEmailVerifyResponse;
import com.example.dto.request.school.SchoolAdminChangePasswordRequest;
import com.example.dto.request.UpdateNameRequest;
import com.example.dto.request.UpdateUsernameRequest;
import com.example.dto.response.Message;
import com.example.dto.response.school.SchoolAdminInfoResponse;
import com.example.dto.response.school.SchoolAdminLoginResponse;
import com.example.model.user.*;
import com.example.service.user.AuthorizationCodeService;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.SchoolService;

import com.example.service.utils.EmailCodeService;
import com.example.service.utils.EmailService;
import com.example.service.utils.PasswordEncodeService;
import com.example.util.JwtTokenUtil;
import jakarta.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Parameter;

import java.util.Objects;


@RestController
@RequestMapping("/api/school-admin")
public class    SchoolAdminManagementController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolAdminManagementController.class);
    private static final Logger operationLogger = LoggerFactory.getLogger("operations.schoolAdministrator");
    private final SchoolAdminService schoolAdminService;
    private final SchoolService schoolService;
    private final AuthorizationCodeService authorizationCodeService;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;
    private final EmailCodeService emailCodeService;

    private final PasswordEncodeService passwordEncodeService;

    @Autowired
    public SchoolAdminManagementController(SchoolAdminService schoolAdminService,
                                           SchoolService schoolService,
                                           AuthorizationCodeService authorizationCodeService,
                                           EmailService emailService,
                                           JwtTokenUtil jwtTokenUtil,
                                           EmailCodeService emailCodeService,
                                           PasswordEncodeService passwordEncodeService
                                           ) {
        this.schoolAdminService = schoolAdminService;
        this.schoolService = schoolService;
        this.authorizationCodeService = authorizationCodeService;
        this.emailService = emailService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.emailCodeService = emailCodeService;
        this.passwordEncodeService = passwordEncodeService;
    }

    @PostMapping("/login")
    public ResponseEntity<SchoolAdminLoginResponse> login(@RequestBody SchoolAdminLoginRequest request) {
        try {
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
        } catch (Exception e) {
            logger.error("学校管理员登陆出现问题 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<SchoolAdminInfoResponse> getSchoolAdminInfo(@PathVariable Long id) {
        try {
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
        }catch (Exception e) {
            logger.error("学校管理员获取信息出现问题 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Message> changePassword(@PathVariable Long id, @RequestBody SchoolAdminChangePasswordRequest request) {
       try {
           Message response = new Message();
           SchoolAdmin admin = schoolAdminService.getSchoolAdminById(id);
           if(admin == null) {
               response.setMessage("用户未找到");
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
           }
           if(!passwordEncodeService.matches(request.getPassword(), admin.getPassword())){
               response.setMessage("旧密码错误");
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
           }
           admin.setPassword(request.getNewPassword());
           schoolAdminService.updatePassword(admin);
           response.setMessage("密码修改成功");
           operationLogger.info("学校管理员 {} 修改密码", admin.info());
           return ResponseEntity.ok(response);
       } catch (Exception e) {
           logger.error("学校管理员修改密码出现问题 {}", e.getMessage(), e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
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
            operationLogger.info("学校管理员 {} 修改用户名", schoolAdmin.info());
            return ResponseEntity.ok(new Message("修改成功"));
        } catch (Exception e) {
            logger.error("学校管理员修改用户名出现问题 {}", e.getMessage(), e);
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
            operationLogger.info("学校管理员 {} 修改姓名", schoolAdmin.info());
            return ResponseEntity.ok(new Message("修改成功"));
        } catch (Exception e) {
            logger.error("学校管理员修改姓名出现问题 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("修改错误" + e.getMessage()));
        }
    }


    @GetMapping("/{id}/bind-email")
    public ResponseEntity<Message> bindEmail(@PathVariable Long id, @RequestParam String newEmail, @RequestParam String code) {
        Message response = new Message();
        try {
            String verificationCode = emailCodeService.getCode("schoolAdmin", newEmail);
            System.out.println(newEmail + " " + verificationCode);
            if(!Objects.equals(verificationCode, code)){
                response.setMessage("验证码错误或已失效");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            schoolAdmin.setEmail(newEmail);
            schoolAdminService.updateSchoolAdmin(schoolAdmin);
            response.setMessage("邮箱设置成功");
            operationLogger.info("学校管理员 {} 设置邮箱为: {}", schoolAdmin.info(), newEmail);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage("邮箱设置失败");
            logger.error("邮箱设置失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/send-email-code")
    public ResponseEntity<SendEmailCodeResponse> sendEmailCode(@RequestParam String email) throws MessagingException {
        try {
            SendEmailCodeResponse response = new SendEmailCodeResponse();
            if (schoolAdminService.emailExist(email)) {
                response.setMessage("邮箱已注册");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            String code = emailService.sendEmail(email);
            emailCodeService.setCode("schoolAdmin", email, code);
            response.setMessage("验证码已发送");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("学校管理员发送验证码出现问题 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/change-email")
    public ResponseEntity<ChangeEmailResponse> changeEmail(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @RequestParam String newEmail, @RequestParam String code) {
        ChangeEmailResponse response = new ChangeEmailResponse();
        try {
            String verificationCode = emailCodeService.getCode("schoolAdmin", newEmail);
            if(!verificationCode.equals(code)){
                response.setMessage("验证码错误或已失效");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(user.getId());
            schoolAdmin.setEmail(newEmail);
            schoolAdminService.updateSchoolAdmin(schoolAdmin);
            response.setMessage("邮箱更换成功");
            response.setNewEmail(newEmail);
            operationLogger.info("学校管理员 {} 更改邮箱为: {}", schoolAdmin.info(), newEmail);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage("邮箱更换失败");
            logger.error("邮箱更换失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

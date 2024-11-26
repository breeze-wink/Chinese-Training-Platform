package com.example.controller;

import com.example.dto.request.SchoolAdminLoginRequest;
import com.example.dto.request.SchoolAdminManagementController.SchoolAdminChangePasswordRequest;
import com.example.dto.response.Message;
import com.example.dto.response.SchoolAdminInfoResponse;
import com.example.dto.response.SchoolAdminLoginResponse;
import com.example.model.user.AuthorizationCode;
import com.example.model.user.School;
import com.example.model.user.SchoolAdmin;
import com.example.service.user.AuthorizationCodeService;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.SchoolService;
import com.example.service.user.impl.AuthorizationCodeServiceImpl;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import com.example.service.user.impl.SchoolServiceImpl;
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

    @Autowired
    public SchoolAdminManagementController(SchoolAdminService schoolAdminService,
                                           SchoolService schoolService,
                                           AuthorizationCodeService authorizationCodeService) {
        this.schoolAdminService = schoolAdminService;
        this.schoolService = schoolService;
        this.authorizationCodeService = authorizationCodeService;
    }

    @PostMapping("/login")
    public ResponseEntity<SchoolAdminLoginResponse> login(@RequestBody SchoolAdminLoginRequest request) {
        String account = request.getAccount();
        String password = request.getPassword();

        SchoolAdmin admin = schoolAdminService.authenticate(account, password);

        if (admin != null) {
            SchoolAdminLoginResponse response = new SchoolAdminLoginResponse();
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
            data.setSchoolName(school.getName());
            if(code == null){
                data.setAuthorizationCode("无");
            }
            else{
                data.setAuthorizationCode(code.getCode());
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
}

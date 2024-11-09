package com.example.controller;

import com.example.dto.request.SystemAdminLoginRequest;
import com.example.dto.response.SystemAdminInfoResponse;
import com.example.dto.response.SystemAdminLoginResponse;
import com.example.service.user.SystemAdminService;
import com.example.service.user.SystemAdminServiceImpl;
import com.example.model.user.SystemAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminController {
    private final SystemAdminService systemAdminService;

    @Autowired
    public SystemAdminController(SystemAdminServiceImpl systemAdminService) {
        this.systemAdminService = systemAdminService;
    }

    @PostMapping("/login")
    public ResponseEntity<SystemAdminLoginResponse> login(@RequestBody SystemAdminLoginRequest loginRequest) {
        String account = loginRequest.getAccount();
        String password = loginRequest.getPassword();

        SystemAdmin admin = systemAdminService.authenticate(account, password);

        if (admin != null) {
            SystemAdminLoginResponse response = new SystemAdminLoginResponse();
            response.setMessage("success");
            response.setId(admin.getId());
            return ResponseEntity.ok(response);
        }
        else {
            SystemAdminLoginResponse response = new SystemAdminLoginResponse();
            response.setMessage("用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<SystemAdminInfoResponse> getSystemAdminInfo(@PathVariable Long id) {
        SystemAdmin admin = systemAdminService.getSystemAdminById(id);
        if (admin != null) {
            SystemAdminInfoResponse response = new SystemAdminInfoResponse();
            response.setId(admin.getId());
            response.setUsername(admin.getUsername());
            response.setEmail(admin.getEmail());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
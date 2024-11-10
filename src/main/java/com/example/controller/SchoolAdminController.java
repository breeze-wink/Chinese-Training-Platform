package com.example.controller;

import com.example.dto.request.SchoolAdminLoginRequest;
import com.example.dto.response.SchoolAdminInfoResponse;
import com.example.dto.response.SchoolAdminLoginResponse;
import com.example.mapper.user.SchoolAdminMapper;
import com.example.model.user.School;
import com.example.model.user.SchoolAdmin;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.SchoolService;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school-admin")
public class SchoolAdminController {
    private final SchoolAdminService schoolAdminService;
    private final SchoolService schoolService;

    @Autowired
    public SchoolAdminController(SchoolAdminServiceImpl schoolAdminService, SchoolService schoolService) {
        this.schoolAdminService = schoolAdminService;
        this.schoolService = schoolService;
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
            response.setMessage("success");
            School school = schoolService.getSchoolById(admin.getSchoolId());
            SchoolAdminInfoResponse.InfoData data = new SchoolAdminInfoResponse.InfoData();
            data.setUsername(admin.getUsername());
            data.setEmail(admin.getEmail());
            data.setSchoolName(school.getName());
            response.setData(data);

            return ResponseEntity.ok(response);
        } else {
            response.setMessage("用户未找到");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

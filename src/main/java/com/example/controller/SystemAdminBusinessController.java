package com.example.controller;


import com.example.dto.request.CreateSchoolAdminRequest;
import com.example.dto.response.Message;
import com.example.model.user.SchoolAdmin;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminBusinessController {
    private SchoolAdminService schoolAdminService;

    @Autowired
    public SystemAdminBusinessController(SchoolAdminServiceImpl schoolAdminService) {
        this.schoolAdminService = schoolAdminService;
    }

    @PostMapping("/create-school-admin")
    public ResponseEntity<Message> createSchoolAdmin(@RequestBody  CreateSchoolAdminRequest request) {
        String name = request.getName();
        String password = request.getPassword();
        Long schoolId = request.getSchoolId();
        Message response = new Message();
        if (schoolAdminService.checkExistSchool(name)) {
            response.setMessage("用户名已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            SchoolAdmin schoolAdmin = new SchoolAdmin();
            schoolAdmin.setUsername(name);
            schoolAdmin.setPassword(password);
            schoolAdmin.setSchoolId(schoolId);
            schoolAdminService.addSchoolAdmin(schoolAdmin);
            response.setMessage("生成成功");
            return ResponseEntity.ok(response);
        } catch (Exception  e) {
            e.printStackTrace();
            response.setMessage("生成失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}

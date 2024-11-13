package com.example.controller;

import com.example.dto.request.SystemAdminLoginRequest;
import com.example.dto.response.CreateStandardResponse;
import com.example.dto.response.Message;
import com.example.dto.response.SystemAdminInfoResponse;
import com.example.dto.response.SystemAdminLoginResponse;
import com.example.model.course.CourseStandard;
import com.example.model.user.SystemAdmin;
import com.example.service.course.CourseStandardService;
import com.example.service.course.impl.CourseStandardServiceImpl;
import com.example.service.user.SystemAdminService;
import com.example.service.user.impl.SystemAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminController {
    private final SystemAdminService systemAdminService;
    private final CourseStandardService courseStandardService;

    @Autowired
    public SystemAdminController(SystemAdminServiceImpl systemAdminService, CourseStandardServiceImpl courseStandardService) {
        this.systemAdminService = systemAdminService;
        this.courseStandardService = courseStandardService;
    }

    @PostMapping("/login")
    public ResponseEntity<SystemAdminLoginResponse> login(@RequestBody SystemAdminLoginRequest loginRequest) {
        String account = loginRequest.getAccount();
        String password = loginRequest.getPassword();

        SystemAdmin admin = systemAdminService.authenticate(account, password);

        SystemAdminLoginResponse response = new SystemAdminLoginResponse();
        if (admin != null) {
            response.setMessage("success");
            response.setId(admin.getId());
            return ResponseEntity.ok(response);
        }
        else {
            response.setMessage("用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<SystemAdminInfoResponse> getSystemAdminInfo(@PathVariable Long id) {
        SystemAdmin admin = systemAdminService.getSystemAdminById(id);

        SystemAdminInfoResponse response = new SystemAdminInfoResponse();
        if (admin != null) {
            response.setMessage("success");

            SystemAdminInfoResponse.InfoData data = new SystemAdminInfoResponse.InfoData();
            data.setId(admin.getId());
            data.setUsername(admin.getUsername());
            data.setEmail(admin.getEmail());
            response.setData(data);

            return ResponseEntity.ok(response);
        } else {
            response.setMessage("用户未找到");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping("/create-course-standard")
    public ResponseEntity<CreateStandardResponse> createStandard(@RequestParam("file") MultipartFile file, @RequestParam("executedDate") LocalDate executedDate) {
        CreateStandardResponse response = new CreateStandardResponse();
        if (file.isEmpty()) {
            response.setMessage("课标创建失败, 文件为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            CourseStandard courseStandard = new CourseStandard();
            courseStandard.setTitle(file.getOriginalFilename());
            courseStandard.setContent(file.getBytes());
            courseStandard.setExecutedDate(executedDate);
            courseStandardService.addCourseStandard(courseStandard);

            response.setMessage("课标创建成功");
            response.setCourseStandardId(courseStandard.getId());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.setMessage("服务器文件读取失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update-course-standard/{id}")
    public ResponseEntity<Message> updateCourseStandard(@PathVariable Long id, @RequestParam("file") MultipartFile file, @RequestParam("executedDate") LocalDate executedDate) {
        Message response = new Message();
        CourseStandard courseStandard = courseStandardService.getCourseStandardById(id);
        if (courseStandard == null) {
            response.setMessage("id无匹配课标");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            courseStandard.setTitle(file.getOriginalFilename());
            courseStandard.setContent(file.getBytes());
            courseStandard.setExecutedDate(executedDate);

            response.setMessage("课表更新成功");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.setMessage("服务器文件读取失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
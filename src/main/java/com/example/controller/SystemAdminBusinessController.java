package com.example.controller;


import com.example.dto.request.system.CreateSchoolAdminRequest;
import com.example.dto.response.Message;
import com.example.dto.response.system.QuestionsResponse;
import com.example.dto.response.system.GetSchoolAdminAccountsResponse;
import com.example.model.question.Question;
import com.example.model.user.School;
import com.example.model.user.SchoolAdmin;
import com.example.service.question.QuestionService;
import com.example.service.question.impl.QuestionServiceImpl;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.SchoolService;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import com.example.service.user.impl.SchoolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminBusinessController {
    private final SchoolAdminService schoolAdminService;
    private final QuestionService questionService;
    private final SchoolService schoolService;

    @Autowired
    public SystemAdminBusinessController(SchoolAdminServiceImpl schoolAdminService,
                                         QuestionServiceImpl questionService,
                                         SchoolServiceImpl schoolService
                                         ) {
        this.schoolAdminService = schoolAdminService;
        this.questionService = questionService;
        this.schoolService = schoolService;
    }

    @GetMapping("/get-school-admin-accounts")
    public ResponseEntity<GetSchoolAdminAccountsResponse> getSchoolAdminAccounts() {
        GetSchoolAdminAccountsResponse response = new GetSchoolAdminAccountsResponse();
        List<SchoolAdmin> schoolAdmins = schoolAdminService.getAllSchoolAdmins();
        List<GetSchoolAdminAccountsResponse.InfoData> data = new ArrayList<>();
        for (SchoolAdmin schoolAdmin : schoolAdmins) {
            GetSchoolAdminAccountsResponse.InfoData infoData = new GetSchoolAdminAccountsResponse.InfoData();
            infoData.setSchoolAdminId(schoolAdmin.getId());
            infoData.setUserName(schoolAdmin.getUsername());
            infoData.setName(schoolAdmin.getName());
            infoData.setEmail(schoolAdmin.getEmail());
            infoData.setSchoolName(schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName());
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("获取成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-school-admin")
    public ResponseEntity<Message> createSchoolAdmin(@RequestBody  CreateSchoolAdminRequest request) {
        String name = request.getName();
        String password = request.getPassword();
        String schoolName = request.getSchoolName();
        Message response = new Message();
        if (schoolAdminService.checkExistUsername(name)) {
            response.setMessage("用户名已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            School school = schoolService.getSchoolByName(schoolName);

            if (school == null) {
                school = new School();
                school.setName(schoolName);
                schoolService.addSchool(school);
            }

            Long schoolId = school.getId();
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


    @DeleteMapping("/delete-school-admin-account/{id}")
    public ResponseEntity<Message> deleteSchoolAdminAccount(@PathVariable Long id) {
        Message response = new Message();
        SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
        if(schoolAdmin == null){
            response.setMessage("删除失败，未找到学校管理员");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else {
            schoolAdminService.removeSchoolAdmin(id);
            response.setMessage("删除成功");
            return ResponseEntity.ok(response);
        }
    }

}

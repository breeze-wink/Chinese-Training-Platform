package com.example.controller;


import com.example.dto.request.system.CreateSchoolAdminRequest;
import com.example.dto.response.Message;
import com.example.dto.response.system.QuestionsResponse;
import com.example.dto.response.system.GetSchoolAdminAccountsResponse;
import com.example.model.question.Question;
import com.example.model.user.BaseUser;
import com.example.model.user.School;
import com.example.model.user.SchoolAdmin;
import com.example.model.user.SystemAdmin;
import com.example.service.question.QuestionService;
import com.example.service.question.impl.QuestionServiceImpl;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.SchoolService;
import com.example.service.user.SystemAdminService;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import com.example.service.user.impl.SchoolServiceImpl;
import com.example.service.user.impl.SystemAdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminBusinessController {
    private static final Logger logger = LoggerFactory.getLogger(SystemAdminBusinessController.class);
    private static final Logger operationLogger = LoggerFactory.getLogger("operations.systemAdministrator");
    private final SchoolAdminService schoolAdminService;
    private final QuestionService questionService;
    private final SystemAdminService systemAdminService;
    private final SchoolService schoolService;

    @Autowired
    public SystemAdminBusinessController(SchoolAdminServiceImpl schoolAdminService,
                                         QuestionServiceImpl questionService,
                                         SchoolServiceImpl schoolService,
                                         SystemAdminServiceImpl systemAdminService
                                         ) {
        this.schoolAdminService = schoolAdminService;
        this.questionService = questionService;
        this.schoolService = schoolService;
        this.systemAdminService = systemAdminService;
    }

    @GetMapping("/get-school-admin-accounts")
    public ResponseEntity<GetSchoolAdminAccountsResponse> getSchoolAdminAccounts(@AuthenticationPrincipal BaseUser user) {
        try {
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
            SystemAdmin systemAdmin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员{}获取了学校管理员账号列表", systemAdmin.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("获取学校管理员账号列表失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create-school-admin")
    public ResponseEntity<Message> createSchoolAdmin(@AuthenticationPrincipal BaseUser user, @RequestBody CreateSchoolAdminRequest request) {
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
            SystemAdmin systemAdmin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员{}生成了学校管理员账号，学校管理员信息为：{}", systemAdmin.info(), schoolAdmin.info());
            return ResponseEntity.ok(response);
        } catch (Exception  e) {
            response.setMessage("生成失败：" + e.getMessage());
            logger.error("生成学校管理员账号失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @DeleteMapping("/delete-school-admin-account/{id}")
    public ResponseEntity<Message> deleteSchoolAdminAccount(@AuthenticationPrincipal BaseUser user, @PathVariable Long id) {
        try {
            Message response = new Message();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            if(schoolAdmin == null){
                response.setMessage("删除失败，未找到学校管理员");
                logger.error("删除学校管理员账号失败，未找到学校管理员");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            else {
                schoolAdminService.removeSchoolAdmin(id);
                response.setMessage("删除成功");

                SystemAdmin admin = systemAdminService.getSystemAdminById(user.getId());
                operationLogger.info("系统管理员{}删除学校管理员账号，学校管理员信息为：{}", admin.info(), schoolAdmin.info());
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            logger.error("删除学校管理员账号失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

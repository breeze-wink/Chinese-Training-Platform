package com.example.controller;

import com.example.service.user.AuthorizationCodeService;
import com.example.service.user.AuthorizationCodeServiceImpl;
import com.example.service.user.TeacherService;
import com.example.service.user.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final AuthorizationCodeService authorizationCodeService;

    @Autowired
    public TeacherController(TeacherServiceImpl teacherService, AuthorizationCodeServiceImpl authorizationCodeService) {
        this.teacherService = teacherService;
        this.authorizationCodeService = authorizationCodeService;
    }
}

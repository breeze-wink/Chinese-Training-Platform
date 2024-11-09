package com.example.controller;

import com.example.service.user.SchoolAdminService;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/school-admin")
public class SchoolAdminController {
    private final SchoolAdminService schoolAdminService;

    @Autowired
    public SchoolAdminController(SchoolAdminServiceImpl schoolAdminService) {
        this.schoolAdminService = schoolAdminService;
    }
}

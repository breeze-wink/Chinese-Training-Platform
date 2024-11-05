package com.example.controller;

import com.example.service.user.SystemAdminService;
import com.example.service.user.SystemAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminController {
    private final SystemAdminService systemAdminService;

    @Autowired
    public SystemAdminController(SystemAdminServiceImpl systemAdminService) {
        this.systemAdminService = systemAdminService;
    }


}

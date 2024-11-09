package com.example.controller;

import com.example.service.user.StudentService;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Student")
public class StudentController {
    private final StudentService studentService;
    private final EmailService emailService;
    @Autowired
    public StudentController(StudentServiceImpl studentService, EmailService emailService) {
        this.studentService = studentService;
        this.emailService = emailService;
    }
}

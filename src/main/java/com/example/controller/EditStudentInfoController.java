package com.example.controller;


import com.example.dto.request.StudentEditInformationRequest;
import com.example.dto.response.StudentEditInformationResponse;
import com.example.model.user.Student;
import com.example.service.user.SchoolService;
import com.example.service.user.StudentService;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class EditStudentInfoController {
    private final StudentService studentService;
    private final EmailService emailService;
    private final SchoolService schoolService;

    @Autowired
    public EditStudentInfoController(StudentServiceImpl studentService, EmailService emailService, SchoolService schoolService) {
        this.studentService = studentService;
        this.emailService = emailService;
        this.schoolService = schoolService;
    }
    @PostMapping("/{id}/editInformation")
    public ResponseEntity<StudentEditInformationResponse> studentEditInformation(@PathVariable Long id, @RequestBody StudentEditInformationRequest request) {
        StudentEditInformationResponse response = new StudentEditInformationResponse();
        Student student = studentService.getStudentById(id);
        if (student == null) {
            response.setMessage("用户未找到");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (studentService.existStudentUsername(request.getUsername()) && !student.getUsername().equals(request.getUsername())) {
            response.setMessage("用户名已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        student.setUsername(request.getUsername());
        student.setName(request.getName());
        student.setGrade(request.getGrade());
        studentService.updateStudent(student);
        response.setMessage("修改成功");
        StudentEditInformationResponse.InfoData data = new StudentEditInformationResponse.InfoData();
        data.setUsername(student.getUsername());
        data.setName(student.getName());
        data.setGrade(student.getGrade());
        return ResponseEntity.ok(response);
    }
}

package com.example.controller;

import com.example.dto.request.StudentController.StudentLoginRequest;
import com.example.dto.request.StudentController.StudentRegisterRequest;
import com.example.dto.request.StudentController.StudentVerifyRequest;
import com.example.dto.response.StudentController.*;
import com.example.model.classes.ClassStudent;
import com.example.model.essay.Essay;
import com.example.model.user.Student;
import com.example.service.classes.ClassService;
import com.example.service.classes.ClassStudentService;
import com.example.service.essay.EssayService;
import com.example.service.user.SchoolService;
import com.example.service.user.StudentService;
import com.example.service.utils.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;
    private final EmailService emailService;
    private final SchoolService schoolService;
    private final EssayService essayService;
    private final ClassStudentService classStudentService;
    private final ClassService classService;

    @Autowired
    public StudentController(StudentService studentService,
                             EmailService emailService,
                             SchoolService schoolService,
                             EssayService essayService,
                             ClassStudentService classStudentService,
                             ClassService classService) {
        this.studentService = studentService;
        this.emailService = emailService;
        this.schoolService = schoolService;
        this.essayService = essayService;
        this.classStudentService = classStudentService;
        this.classService = classService;
    }

    @PostMapping("/login")
    public ResponseEntity<StudentLoginResponse> login(@RequestBody StudentLoginRequest request) {
        String account = request.getAccount();
        String password = request.getPassword();

        Student student = studentService.authenticate(account, password);

        StudentLoginResponse response = new StudentLoginResponse();
        if (student != null) {
            response.setMessage("success");
            response.setId(student.getId());
            return ResponseEntity.ok(response);
        }
        else {
            response.setMessage("用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/send-verification")
    public ResponseEntity<StudentVerifyResponse> sendVerificationCode(@RequestBody StudentVerifyRequest request) throws MessagingException {
        String email = request.getEmail();
        StudentVerifyResponse response = new StudentVerifyResponse();
        if (studentService.existStudent(email)) {
            response.setMessage("邮箱已注册");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String verificationCode = emailService.sendEmail(email);
        response.setVerificationCode(verificationCode);
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<StudentRegisterResponse> studentRegister(@RequestBody StudentRegisterRequest request) {
        StudentRegisterResponse response = new StudentRegisterResponse();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();
        if (studentService.existStudentUsername(request.getUsername())) {
            response.setMessage("用户名已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (!password.equals(confirmPassword)) {
            response.setMessage("密码输入不一致");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Student student = new Student();
        student.setEmail(request.getEmail());
        student.setUsername(request.getUsername());
        student.setPassword(request.getPassword());

        studentService.addStudent(student);
        response.setId(student.getId());
        response.setMessage("注册成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentInfoResponse> getStudentInfo(@PathVariable Long id) {
        StudentInfoResponse response = new StudentInfoResponse();
        Student student = studentService.getStudentById(id);
        List<ClassStudent> classStudents = classStudentService.getClassStudentsByStudentId(id);
        ClassStudent classStudent = null;
        if(classStudents.size() == 1){
            classStudent = classStudents.get(0);
        }
        if (student == null) {
            response.setMessage("用户未找到");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setMessage("Success");
        StudentInfoResponse.InfoData data = new StudentInfoResponse.InfoData();
        data.setUsername(student.getUsername());
        data.setEmail(student.getEmail());
        data.setName(student.getName());
        data.setGrade(student.getGrade());
        data.setSchoolName(null);
        data.setClassName(null);
        if (student.getSchoolId() != null) {
            data.setSchoolName(schoolService.getSchoolById(student.getSchoolId()).getName());
        }
        if(classStudent != null){
            data.setClassName(classService.getClassById(classStudent.getClassId()).getName());
        }

        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/view-essays")
    public ResponseEntity<GetEssaysResponse> getEssays(@PathVariable Long id) {
        GetEssaysResponse response = new GetEssaysResponse();
        List<Essay> essays = essayService.getAllEssays();
        List<GetEssaysResponse.InfoData> data = new ArrayList<>();
        for(Essay essay : essays){
            GetEssaysResponse.InfoData infoData = new GetEssaysResponse.InfoData();
            infoData.setId(essay.getId());
            infoData.setTitle(essay.getTitle());
            data.add(infoData);
        }
        response.setInfoData(data);
        response.setMessage("作文查询成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/essay/get-info/{essayId}")
    public ResponseEntity<InputStreamResource> getEssayInfo(@PathVariable Long id, @PathVariable Long essayId) {
        Essay essay = essayService.getEssayById(essayId);
        if (essay == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(essay.getContent());
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + essay.getTitle())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(essay.getContent().length)
                .body(resource);
    }

}

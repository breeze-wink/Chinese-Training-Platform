package com.example.controller;

import com.example.dto.request.student.StudentLoginRequest;
import com.example.dto.request.student.StudentRegisterRequest;
import com.example.dto.request.student.StudentVerifyRequest;
import com.example.dto.response.student.*;
import com.example.model.classes.ClassStudent;
import com.example.model.essay.Essay;
import com.example.model.user.Student;
import com.example.service.classes.ClassService;
import com.example.service.classes.ClassStudentService;
import com.example.service.essay.EssayService;
import com.example.service.user.SchoolService;
import com.example.service.user.StudentService;
import com.example.service.utils.EmailCodeService;
import com.example.service.utils.EmailService;
import com.example.util.JwtTokenUtil;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private static final Logger operationLogger = LoggerFactory.getLogger("operations.student");
    private final StudentService studentService;
    private final EmailService emailService;
    private final SchoolService schoolService;
    private final EssayService essayService;
    private final ClassStudentService classStudentService;
    private final ClassService classService;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailCodeService emailCodeService;

    @Autowired
    public StudentController(StudentService studentService,
                             EmailService emailService,
                             SchoolService schoolService,
                             EssayService essayService,
                             ClassStudentService classStudentService,
                             ClassService classService,
                             JwtTokenUtil jwtTokenUtil,
                             EmailCodeService EmailCodeService
                             ) {
        this.studentService = studentService;
        this.emailService = emailService;
        this.schoolService = schoolService;
        this.essayService = essayService;
        this.classStudentService = classStudentService;
        this.classService = classService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.emailCodeService = EmailCodeService;
    }

    @PostMapping("/login")
    public ResponseEntity<StudentLoginResponse> login(@RequestBody StudentLoginRequest request) {
        String account = request.getAccount();
        String password = request.getPassword();

        Student student = studentService.authenticate(account, password);

        StudentLoginResponse response = new StudentLoginResponse();
        if (student != null) {
            // 生成JWT
            String jwt = jwtTokenUtil.generateToken(student);
            response.setToken(jwt);
            response.setMessage("success");
            response.setId(student.getId());
            return ResponseEntity.ok(response);
        } else {
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
        String code = emailService.sendEmail(email);
        emailCodeService.setCode("student", email, code);
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<StudentRegisterResponse> studentRegister(@RequestBody StudentRegisterRequest request) {
        StudentRegisterResponse response = new StudentRegisterResponse();
        String email = request.getEmail();
        String code = emailCodeService.getCode("student", email);
        if(!code.equals(request.getVerificationCode())){
            response.setMessage("验证码错误或已失效");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
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
        try {
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
        }catch (Exception e){
            logger.error("学生个人信息获取失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/view-essays")
    public ResponseEntity<GetEssaysResponse> getEssays(@PathVariable Long id) {
        try {
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
        } catch (Exception e){
            logger.error("学生作文获取失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/essay/get-info/{essayId}")
    public ResponseEntity<InputStreamResource> getEssayInfo(@PathVariable Long id, @PathVariable Long essayId) {
        try {
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
        } catch (Exception e){
            logger.error("获取作文文件失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

package com.example.controller;

import com.example.dto.request.system.CreateKnowledgePointRequest;
import com.example.dto.request.system.SystemAdminChangePasswordRequest;
import com.example.dto.request.system.SystemAdminLoginRequest;
import com.example.dto.response.*;
import com.example.dto.response.system.*;
import com.example.model.course.CourseStandard;
import com.example.model.course.KnowledgePoint;
import com.example.model.user.BaseUser;

import com.example.model.user.SchoolAdmin;
import com.example.model.user.SystemAdmin;
import com.example.service.course.CourseStandardService;
import com.example.service.course.KnowledgePointService;
import com.example.service.course.impl.CourseStandardServiceImpl;
import com.example.service.course.impl.KnowledgePointServiceImpl;
import com.example.service.user.SystemAdminService;
import com.example.service.user.impl.SystemAdminServiceImpl;
import com.example.service.utils.EmailCodeService;
import com.example.service.utils.EmailService;
import com.example.service.utils.PasswordEncodeService;
import com.example.util.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminController {
    private static final Logger logger = LoggerFactory.getLogger(SystemAdminController.class);
    private static final Logger operationLogger = LoggerFactory.getLogger("operations.systemAdministrator");
    private final SystemAdminService systemAdminService;
    private final CourseStandardService courseStandardService;
    private final KnowledgePointService knowledgePointService;

    private final PasswordEncodeService passwordEncodeService;
    private final JwtTokenUtil jwtTokenUtil;
    private final EmailService emailService;
    private final EmailCodeService emailCodeService;

    @Autowired
    public SystemAdminController(SystemAdminServiceImpl systemAdminService,
                                 CourseStandardServiceImpl courseStandardService,
                                 KnowledgePointServiceImpl knowledgePointService,
                                 JwtTokenUtil jwtTokenUtil,
                                 EmailService emailService,
                                 EmailCodeService emailCodeService,
                                 PasswordEncodeService passwordEncodeService
                                 ) {
        this.systemAdminService = systemAdminService;
        this.courseStandardService = courseStandardService;
        this.knowledgePointService = knowledgePointService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.emailService = emailService;
        this.emailCodeService = emailCodeService;
        this.passwordEncodeService = passwordEncodeService;
    }

    @PostMapping("/login")
    public ResponseEntity<SystemAdminLoginResponse> login(@RequestBody SystemAdminLoginRequest loginRequest) {
        try {
            String account = loginRequest.getAccount();
            String password = loginRequest.getPassword();

            SystemAdmin admin = systemAdminService.authenticate(account, password);

            SystemAdminLoginResponse response = new SystemAdminLoginResponse();
            if (admin != null) {
                String token = jwtTokenUtil.generateToken(admin);
                response.setToken(token);
                response.setMessage("success");
                response.setId(admin.getId());
                return ResponseEntity.ok(response);
            } else {
                response.setMessage("用户名或密码错误");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            logger.error("系统管理员登陆出现问题 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemAdminInfoResponse> getSystemAdminInfo(@PathVariable Long id) {
        SystemAdmin admin = systemAdminService.getSystemAdminById(id);

        try {
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
        } catch (Exception e) {
            logger.error("系统管理员获取信息出现问题 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create-course-standard")
    public ResponseEntity<CreateStandardResponse> createStandard(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @RequestParam("file") MultipartFile file, @RequestParam("executedDate") LocalDate executedDate) {
        CreateStandardResponse response = new CreateStandardResponse();
        if (file.isEmpty()) {
            response.setMessage("课标创建失败, 文件为空");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            CourseStandard courseStandard = new CourseStandard();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename != null ? originalFilename.replaceAll("\\.[^.]*$", "") : "";
            courseStandard.setTitle(title);
            courseStandard.setContent(file.getBytes());
            courseStandard.setExecutedDate(executedDate);
            courseStandardService.addCourseStandard(courseStandard);

            response.setMessage("课标创建成功");
            response.setCourseStandardId(courseStandard.getId());

            SystemAdmin systemAdmin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员{}创建了课标 {}", courseStandard.getId(), systemAdmin.info());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.setMessage("服务器文件读取失败");
            logger.error("文件读取失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update-course-standard/{id}")
    public ResponseEntity<Message> updateCourseStandard(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user,
                                                        @PathVariable Long id,
                                                        @RequestParam("file") MultipartFile file,
                                                        @RequestParam("executedDate") LocalDate executedDate) {
        Message response = new Message();
        CourseStandard courseStandard = courseStandardService.getCourseStandardById(id);
        if (courseStandard == null) {
            response.setMessage("id无匹配课标");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename != null ? originalFilename.replaceAll("\\.[^.]*$", "") : "";
            courseStandard.setTitle(title);
            courseStandard.setContent(file.getBytes());
            courseStandard.setExecutedDate(executedDate);

            courseStandardService.updateCourseStandard(courseStandard);
            response.setMessage("课标更新成功");
            SystemAdmin systemAdmin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员{}更新了课标 {}", systemAdmin.info(), courseStandard.info());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.setMessage("服务器文件读取失败");
            logger.error("文件读取失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("query-course-standard/{id}")
    public ResponseEntity<InputStreamResource> getCourseStandard(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @PathVariable Long id) {
        try {
            CourseStandard courseStandard = courseStandardService.getCourseStandardById(id);
            if (courseStandard == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(courseStandard.getContent());
            InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

            SystemAdmin systemAdmin = systemAdminService.getSystemAdminById(user.getId());
            // 使用 ContentDisposition 构建符合 RFC 5987 的头部
            ContentDisposition contentDisposition = ContentDisposition
                    .inline()
                    .filename(courseStandard.getTitle(), StandardCharsets.UTF_8)
                    .build();
            operationLogger.info("系统管理员 {} 查询了课标 {}", systemAdmin.info(), courseStandard.info());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(courseStandard.getContent().length)
                    .body(resource);
        } catch (Exception e) {
            logger.error("获取课标文件失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/delete-course-standard/{id}")
    public ResponseEntity<Message> deleteCourseStandard(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @PathVariable Long id) {
        try {
            Message response = new Message();
            CourseStandard courseStandard = courseStandardService.getCourseStandardById(id);
            if (courseStandard == null) {
                response.setMessage("id不匹配，课标删除失败");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            courseStandardService.removeCourseStandard(id);
            SystemAdmin systemAdmin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员 {} 删除了课标 {}", systemAdmin.info(), courseStandard.info());
            response.setMessage("删除课标成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("删除课标失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-all-course-standards")
    public ResponseEntity<GetAllCourseStandardResponse> getAllCourseStandard(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user) {
        List<CourseStandard> courseStandards = courseStandardService.getAllCourseStandards();
        GetAllCourseStandardResponse response = new GetAllCourseStandardResponse();
        response.setCourseStandardInfos(new ArrayList<>());
        try {
            for (CourseStandard courseStandard : courseStandards) {
                GetAllCourseStandardResponse.CourseStandardInfo info = new GetAllCourseStandardResponse.CourseStandardInfo();
                info.setId(courseStandard.getId());
                info.setTitle(courseStandard.getTitle());
                info.setExecutedDate(courseStandard.getExecutedDate().toString());
                response.getCourseStandardInfos().add(info);
            }
            response.setMessage("课标获取成功");

            SystemAdmin admin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员 {} 获取了全部课标", admin.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("课标获取失败，服务器出错" + e.getMessage());
            logger.error("获取全部课标失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/create-knowledge-point")
    public ResponseEntity<CreateKnowledgePointResponse> createKnowledgePoint(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @RequestBody CreateKnowledgePointRequest request) {
        CreateKnowledgePointResponse response = new CreateKnowledgePointResponse();
        KnowledgePoint knowledgePoint = new KnowledgePoint();
        knowledgePoint.setName(request.getName());
        knowledgePoint.setDescription(request.getDescription());
        knowledgePoint.setType(request.getType());
        try {
            knowledgePointService.addKnowledgePoint(knowledgePoint);
            response.setKnowledgePointId(knowledgePoint.getId());
            response.setMessage("知识点创建成功");
            SystemAdmin admin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员 {} 创建了知识点 {}", admin.info(), knowledgePoint.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("创建知识点失败 ");
            logger.error("创建知识点失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete-knowledge-point/{id}")
    public ResponseEntity<Message> deleteKnowledgePoint(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @PathVariable Long id) {
        Message response = new Message();
        KnowledgePoint knowledgePoint = knowledgePointService.getKnowledgePointById(id);
        if (knowledgePoint == null) {
            response.setMessage("知识点删除失败，id不匹配");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            knowledgePointService.removeKnowledgePoint(id);
            response.setMessage("知识点删除成功");
            SystemAdmin admin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员 {} 删除了知识点 {}", admin.info(), knowledgePoint.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("删除知识点失败");
            logger.error("删除知识点失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/update-knowledge-point/{id}")
    public ResponseEntity<Message> updateKnowledgePoint(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user,
                                                        @PathVariable Long id,
                                                        @RequestBody CreateKnowledgePointRequest request) {
        Message response = new Message();
        KnowledgePoint knowledgePoint = knowledgePointService.getKnowledgePointById(id);
        if (knowledgePoint == null) {
            response.setMessage("知识点更新失败，id不匹配");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            knowledgePoint.setName(request.getName());
            knowledgePoint.setDescription(request.getDescription());
            knowledgePoint.setType(request.getType());

            knowledgePointService.updateKnowledgePoint(knowledgePoint);

            response.setMessage("知识点更新成功");
            SystemAdmin admin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员 {} 更新了知识点 {}", admin.info(), knowledgePoint.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("知识点更新失败");
            logger.error("知识点更新失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/query-knowledge-point/{id}")
    public ResponseEntity<QueryKnowledgePointResponse> queryKnowledgePoint(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user,
                                                                           @PathVariable Long id) {
        try {
            QueryKnowledgePointResponse response = new QueryKnowledgePointResponse();
            response.setData(new QueryKnowledgePointResponse.KnowledgePointInfo());
            KnowledgePoint knowledgePoint = knowledgePointService.getKnowledgePointById(id);

            if (knowledgePoint == null) {
                response.setMessage("知识点查询失败，id不匹配");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            response.getData().setDescription(knowledgePoint.getDescription());
            response.getData().setType(knowledgePoint.getType());
            response.getData().setName(knowledgePoint.getName());

            response.setMessage("知识点查询成功");
            SystemAdmin admin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员 {} 查询了知识点 {}", admin.info(), knowledgePoint.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("知识点查询失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-all-knowledge-points")
    public ResponseEntity<GetAllKnowledgePointsResponse> getAllKnowledgePoints(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user) {
        List<KnowledgePoint> knowledgePoints = knowledgePointService.getAllKnowledgePoints();
        GetAllKnowledgePointsResponse response = new GetAllKnowledgePointsResponse();
        response.setKnowledgePointInfos(new ArrayList<>());
        try {
            for (KnowledgePoint knowledgePoint : knowledgePoints) {
                GetAllKnowledgePointsResponse.KnowledgePointInfo info = new GetAllKnowledgePointsResponse.KnowledgePointInfo();
                info.setId(knowledgePoint.getId());
                info.setName(knowledgePoint.getName());
                info.setDescription(knowledgePoint.getDescription());
                info.setType(knowledgePoint.getType());
                response.getKnowledgePointInfos().add(info);
            }
            response.setMessage("知识点获取成功");
            SystemAdmin admin = systemAdminService.getSystemAdminById(user.getId());
            operationLogger.info("系统管理员 {} 获取了所有知识点", admin.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("知识点获取失败，服务器出错" + e.getMessage());
            logger.error("知识点获取失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Message> changePassword(@PathVariable Long id, @RequestBody SystemAdminChangePasswordRequest request) {
        Message response = new Message();
        try {
            SystemAdmin systemAdmin = systemAdminService.getSystemAdminById(id);
            if (systemAdmin == null) {
                response.setMessage("用户不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if (!passwordEncodeService.matches(request.getPassword(), systemAdmin.getPassword())) {
                response.setMessage("旧密码错误");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            systemAdmin.setPassword(request.getNewPassword());
            systemAdminService.updatePassword(systemAdmin);
            response.setMessage("密码修改成功");
            operationLogger.info("系统管理员 {} 修改密码", systemAdmin.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("密码修改失败");
            logger.error("密码修改失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/send-email-code")
    public ResponseEntity<SendEmailCodeResponse> sendEmailCode(@RequestParam String email) throws MessagingException {
        SendEmailCodeResponse response = new SendEmailCodeResponse();
        if (systemAdminService.emailExist(email)) {
            response.setMessage("邮箱已注册");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String code = emailService.sendEmail(email);
        emailCodeService.setCode("systemAdmin", email, code);
        response.setMessage("验证码已发送");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/change-email")
    public ResponseEntity<ChangeEmailResponse> changeEmail(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @RequestParam String newEmail, @RequestParam String code) {
        ChangeEmailResponse response = new ChangeEmailResponse();
        try {
            String verifyCode = emailCodeService.getCode("systemAdmin", newEmail);
            if (!verifyCode.equals(code)) {
                response.setMessage("验证码错误或已失效");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            SystemAdmin systemAdmin = systemAdminService.getSystemAdminById(user.getId());
            systemAdmin.setEmail(newEmail);
            systemAdminService.updateSystemAdmin(systemAdmin);
            response.setMessage("邮箱更换成功");
            response.setNewEmail(newEmail);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage("邮箱更换失败");
            logger.error("邮箱更换失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
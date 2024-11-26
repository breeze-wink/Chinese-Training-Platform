package com.example.controller;

import com.example.dto.request.CreateKnowledgePointRequest;
import com.example.dto.request.SystemAdminLoginRequest;
import com.example.dto.response.*;
import com.example.model.course.CourseStandard;
import com.example.model.course.KnowledgePoint;
import com.example.model.user.SystemAdmin;
import com.example.service.course.CourseStandardService;
import com.example.service.course.KnowledgePointService;
import com.example.service.course.impl.CourseStandardServiceImpl;
import com.example.service.course.impl.KnowledgePointServiceImpl;
import com.example.service.user.SystemAdminService;
import com.example.service.user.impl.SystemAdminServiceImpl;
import org.springframework.beans.StandardBeanInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminController {
    private final SystemAdminService systemAdminService;
    private final CourseStandardService courseStandardService;

    private final KnowledgePointService knowledgePointService;

    @Autowired
    public SystemAdminController(SystemAdminServiceImpl systemAdminService,
                                 CourseStandardServiceImpl courseStandardService,
                                 KnowledgePointServiceImpl knowledgePointService) {
        this.systemAdminService = systemAdminService;
        this.courseStandardService = courseStandardService;
        this.knowledgePointService = knowledgePointService;
    }

    @PostMapping("/login")
    public ResponseEntity<SystemAdminLoginResponse> login(@RequestBody SystemAdminLoginRequest loginRequest) {
        String account = loginRequest.getAccount();
        String password = loginRequest.getPassword();

        SystemAdmin admin = systemAdminService.authenticate(account, password);

        SystemAdminLoginResponse response = new SystemAdminLoginResponse();
        if (admin != null) {
            response.setMessage("success");
            response.setId(admin.getId());
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemAdminInfoResponse> getSystemAdminInfo(@PathVariable Long id) {
        SystemAdmin admin = systemAdminService.getSystemAdminById(id);

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
    }

    @PostMapping("/create-course-standard")
    public ResponseEntity<CreateStandardResponse> createStandard(@RequestParam("file") MultipartFile file, @RequestParam("executedDate") LocalDate executedDate) {
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
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.setMessage("服务器文件读取失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update-course-standard/{id}")
    public ResponseEntity<Message> updateCourseStandard(@PathVariable Long id, @RequestParam("file") MultipartFile file, @RequestParam("executedDate") LocalDate executedDate) {
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
            response.setMessage("课表更新成功");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.setMessage("服务器文件读取失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("query-course-standard/{id}")
    public ResponseEntity<InputStreamResource> getCourseStandard(@PathVariable Long id) {
        CourseStandard courseStandard = courseStandardService.getCourseStandardById(id);
        if (courseStandard == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(courseStandard.getContent());
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + courseStandard.getTitle())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(courseStandard.getContent().length)
                .body(resource);
    }

    @DeleteMapping("/delete-course-standard/{id}")
    public ResponseEntity<Message> deleteCourseStandard(@PathVariable Long id) {
        Message response = new Message();
        CourseStandard courseStandard = courseStandardService.getCourseStandardById(id);
        if (courseStandard == null) {
            response.setMessage("id不匹配，课标删除失败");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        courseStandardService.removeCourseStandard(id);
        response.setMessage("删除课标成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-course-standards")
    public ResponseEntity<GetAllCourseStandardResponse> getAllCourseStandard() {
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
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("课标获取失败，服务器出错" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/create-knowledge-point")
    public ResponseEntity<CreateKnowledgePointResponse> createKnowledgePoint(@RequestBody CreateKnowledgePointRequest request) {
        CreateKnowledgePointResponse response = new CreateKnowledgePointResponse();
        KnowledgePoint knowledgePoint = new KnowledgePoint();
        knowledgePoint.setName(request.getName());
        knowledgePoint.setDescription(request.getDescription());
        knowledgePoint.setType(request.getType());
        try {
            knowledgePointService.addKnowledgePoint(knowledgePoint);
            response.setKnowledgePointId(knowledgePoint.getId());
            response.setMessage("知识点创建成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete-knowledge-point/{id}")
    public ResponseEntity<Message> deleteKnowledgePoint(@PathVariable Long id) {
        Message response = new Message();
        KnowledgePoint knowledgePoint = knowledgePointService.getKnowledgePointById(id);
        if (knowledgePoint == null) {
            response.setMessage("知识点删除失败，id不匹配");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        try {
            knowledgePointService.removeKnowledgePoint(id);
            response.setMessage("知识点删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/update-knowledge-point/{id}")
    public ResponseEntity<Message> updateKnowledgePoint(@PathVariable Long id, @RequestBody CreateKnowledgePointRequest request) {
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
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/query-knowledge-point/{id}")
    public ResponseEntity<QueryKnowledgePointResponse> queryKnowledgePoint(@PathVariable Long id) {
        QueryKnowledgePointResponse response = new QueryKnowledgePointResponse();
        response.setData(new QueryKnowledgePointResponse.KnowledgePointInfo());
        KnowledgePoint knowledgePoint = knowledgePointService.getKnowledgePointById(id);

        if (knowledgePoint == null) {
            response.setMessage("知识点查询失败，id不匹配");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.getData().setDescription(knowledgePoint.getDescription());
        response.getData().setName(knowledgePoint.getName());

        response.setMessage("知识点查询成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-knowledge-points")
    public ResponseEntity<GetAllKnowledgePointsResponse> getAllKnowledgePoints() {
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
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("知识点获取失败，服务器出错" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
}
package com.example.controller;

import com.example.dto.request.CreateGroupRequest;
import com.example.dto.request.TeacherCreateClassRequest;
import com.example.dto.response.*;
import com.example.model.classes.ClassGroup;
import com.example.model.classes.ClassStudent;
import com.example.model.classes.Clazz;
import com.example.model.course.CourseStandard;
import com.example.service.classes.ClassGroupService;
import com.example.service.classes.ClassService;
import com.example.service.classes.ClassStudentService;
import com.example.service.classes.impl.ClassGroupServiceImpl;
import com.example.service.classes.impl.ClassServiceImpl;
import com.example.service.classes.impl.ClassStudentServiceImpl;
import com.example.service.course.CourseStandardService;
import com.example.service.course.impl.CourseStandardServiceImpl;
import com.example.service.user.StudentService;
import com.example.service.user.impl.StudentServiceImpl;
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
@RequestMapping("/api/teacher")
public class TeacherBusinessController {
    private final CourseStandardService courseStandardService;
    private final ClassService classService;
    private final ClassGroupService classGroupService;
    private final ClassStudentService classStudentService;
    private final StudentService studentService;
    @Autowired
    public TeacherBusinessController(CourseStandardServiceImpl courseStandardService,
                                     ClassServiceImpl classService,
                                     ClassGroupServiceImpl classGroupService,
                                     ClassStudentServiceImpl classStudentService,
                                     StudentServiceImpl studentService) {
        this.courseStandardService = courseStandardService;
        this.classService = classService;
        this.classGroupService = classGroupService;
        this.classStudentService = classStudentService;
        this.studentService = studentService;
    }

    @GetMapping("/{id}/view-curriculum-standard")
    public ResponseEntity<InputStreamResource> viewCurriculumStandard(@PathVariable Long id) {
        CourseStandard courseStandard = courseStandardService.getCourseStandardAhead();
        if (courseStandard == null || courseStandard.getContent() == null) {
            return ResponseEntity.status(404).body(null);
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(courseStandard.getContent());
        InputStreamResource resource = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + courseStandard.getTitle())
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(courseStandard.getContent().length)
                .body(resource);
    }

    @PostMapping("/{id}/create-class")
    public ResponseEntity<TeacherCreateClassResponse> createClass(@PathVariable Long id, @RequestBody TeacherCreateClassRequest request) {
        TeacherCreateClassResponse response = new TeacherCreateClassResponse();
        Clazz clazz = classService.createClass(request.getClassName(), request.getClassDescription(), id);
        if (clazz == null) {
            response.setMessage("班级创建失败");
            response.setClassCode(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setMessage("班级创建成功");
        response.setClassCode(clazz.getInviteCode());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/get-classes")
    public ResponseEntity<TeacherGetClassesResponse> getClasses(@PathVariable Long id){
        TeacherGetClassesResponse response = new TeacherGetClassesResponse();
        List<Clazz> classes = classService.getClassesByTeacherId(id);
        List<TeacherGetClassesResponse.InfoData> infoDataList = new ArrayList<>();
        for (Clazz clazz : classes) {
            TeacherGetClassesResponse.InfoData infoData = new TeacherGetClassesResponse.InfoData();
            infoData.setClassId(clazz.getId());
            infoData.setClassName(clazz.getName());
            infoData.setClassDescription(clazz.getDescription());
            infoData.setClassCode(clazz.getInviteCode());
            infoDataList.add(infoData);
        }
        response.setData(infoDataList);
        response.setMessage("班级信息获取成功");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}/get-groups")
    public ResponseEntity<GetGroupsResponse> getGroups(@PathVariable Long id) {
        List<ClassGroup> groups = classGroupService.getGroupsByCreatorId(id);
        GetGroupsResponse response = new GetGroupsResponse();

        if (groups.isEmpty()) {
            response.setMessage("获取小组信息失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        response.setData(new ArrayList<>());

        for (ClassGroup group : groups) {
            GetGroupsResponse.GroupInfo info = new GetGroupsResponse.GroupInfo();
            info.setGroupId(group.getId());
            info.setGroupName(group.getName());
            info.setGroupDescription(group.getDescription());
            Clazz clazz = classService.getClassById(group.getClassId());
            info.setClassName(clazz.getName());
            response.getData().add(info);
        }
        response.setMessage("小组信息获取成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/create-group")
    public ResponseEntity<Message> createGroup(@RequestBody CreateGroupRequest request ) {
        Message response = new Message();

        try {
            ClassGroup group = classGroupService.createGroup(request.getClassId(), request.getGroupName(), request.getGroupDescription());
            Long groupId = group.getId();
            for (Long studentId : request.getStudentIds()) {
                classGroupService.addStudentToGroup(groupId, studentId);
            }

            response.setMessage("小组创建成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage("小组创建失败" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}/get-class-members")
    public ResponseEntity<TeacherGetClassMembersResponse> getClassMembers(@PathVariable Long id, @RequestParam Long classId) {
        TeacherGetClassMembersResponse response = new TeacherGetClassMembersResponse();
        List<ClassStudent> classStudents = classStudentService.getClassStudentsByClassId(classId);
        List<TeacherGetClassMembersResponse.InfoData> infoDataList = new ArrayList<>();
        for (ClassStudent classStudent : classStudents) {
            TeacherGetClassMembersResponse.InfoData infoData = new TeacherGetClassMembersResponse.InfoData();
            infoData.setStudentId(classStudent.getStudentId());
            infoData.setStudentName(studentService.getStudentById(classStudent.getStudentId()).getName());
            infoDataList.add(infoData);
        }
        response.setData(infoDataList);
        response.setMessage("班级成员信息获取成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/classes/disband")
    public ResponseEntity<Message> disbandClass(@PathVariable Long id, @RequestParam Long classId) {
        Message response = new Message();
        if (classService.removeClass(classId) == 1) {
            response.setMessage("班级解散成功");
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("班级解散失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

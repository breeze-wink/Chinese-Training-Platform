package com.example.controller;

import com.example.dto.request.school.CreateManagerRequest;
import com.example.dto.response.*;
import com.example.dto.response.school.*;
import com.example.dto.response.teacher.ClassKnowledgePointStatusResponse;
import com.example.model.classes.ClassGroup;
import com.example.model.classes.ClassStudent;
import com.example.model.classes.Clazz;
import com.example.model.user.*;
import com.example.model.view.StudentStatsView;
import com.example.service.classes.ClassGroupService;
import com.example.service.classes.ClassService;
import com.example.service.classes.ClassStudentService;
import com.example.service.classes.impl.ClassGroupServiceImpl;
import com.example.service.classes.impl.ClassServiceImpl;
import com.example.service.classes.impl.ClassStudentServiceImpl;
import com.example.service.user.*;
import com.example.service.user.impl.AuthorizationCodeServiceImpl;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.user.impl.TeacherServiceImpl;
import com.example.service.view.StudentStatsViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/api/school-admin")
public class SchoolAdminBusinessController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolAdminBusinessController.class);
    private static final Logger operationLogger = LoggerFactory.getLogger("operations.schoolAdministrator");
    private final SchoolAdminService schoolAdminService;
    private final ClassService classService;
    private final TeacherService teacherService;
    private final StudentStatsViewService studentStatsViewService;
    private final SchoolService schoolService;
    private final AuthorizationCodeService authorizationCodeService;
    private final StudentService studentService;
    private final ClassStudentService classStudentService;
    private final ClassGroupService classGroupService;
    @Autowired
    public SchoolAdminBusinessController(SchoolAdminServiceImpl schoolAdminService,
                                         ClassServiceImpl classService,
                                         TeacherServiceImpl teacherService,
                                         AuthorizationCodeServiceImpl authorizationCodeService,
                                         StudentServiceImpl studentService,
                                         ClassStudentServiceImpl classStudentService,
                                         ClassGroupServiceImpl classGroupService,
                                         SchoolService schoolService,
                                         StudentStatsViewService studentStatsViewService
                                         ) {
        this.schoolAdminService = schoolAdminService;
        this.classService = classService;
        this.teacherService = teacherService;
        this.authorizationCodeService = authorizationCodeService;
        this.studentService = studentService;
        this.classStudentService = classStudentService;
        this.classGroupService = classGroupService;
        this.schoolService = schoolService;
        this.studentStatsViewService = studentStatsViewService;
    }

    @GetMapping("/{id}/generate-authorization-code")
    public ResponseEntity<GenerateAuthorizationCodeResponse> generateAuthorizationCode(@PathVariable Long id) {
        try{
            GenerateAuthorizationCodeResponse response = new GenerateAuthorizationCodeResponse();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            if (schoolAdmin == null) {
                response.setMessage("用户未找到");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Random random = new Random();
            String[] charsToCode = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            StringBuilder code = new StringBuilder();
            extracted(code, charsToCode, random);
            while(authorizationCodeService.codeIsExist(code.toString())){
                extracted(code, charsToCode, random);
            }
            String schoolName = schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName();

            if(authorizationCodeService.schoolIsExist(schoolAdmin.getSchoolId())){
                AuthorizationCode authorizationCode = new AuthorizationCode();
                authorizationCode.setCode(code.toString());
                authorizationCode.setSchoolId(schoolAdmin.getSchoolId());
                authorizationCode.setCreateDate(LocalDate.now());
                int result = authorizationCodeService.updateAuthorizationCode(authorizationCode);
                if(result != 0){
                    response.setMessage("授权码更新成功");
                    response.setCode(code.toString());
                    response.setCreateDate(LocalDate.now().toString());
                    operationLogger.info("学校管理员 {} 为学校 {} 生成授权码", schoolAdmin.info(), schoolName);
                    return ResponseEntity.ok(response);
                }
                response.setMessage("授权码更新失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }else{
                AuthorizationCode authorizationCode = new AuthorizationCode();
                authorizationCode.setCode(code.toString());
                authorizationCode.setSchoolId(schoolAdmin.getSchoolId());
                int result = authorizationCodeService.addAuthorizationCode(authorizationCode);
                if(result != 0){
                    response.setMessage("授权码创建成功");
                    response.setCode(code.toString());
                    operationLogger.info("学校管理员 {} 为学校 {} 生成授权码", schoolAdmin.info(), schoolName);
                    return ResponseEntity.ok(response);
                }
                response.setMessage("授权码创建失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            logger.error("生成授权码失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    private static void extracted(StringBuilder code, String[] charsToCode, Random random) {
        for (int i = 0; i < 8; ++ i) {
            code.append(charsToCode[random.nextInt(36)]);
        }
    }

    @DeleteMapping("{id}/delete-teacher/{teacherId}")
    public ResponseEntity<Message> deleteTeacher(@PathVariable Long id, @PathVariable Long teacherId) {
        try {
            Message response = new Message();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            List<Clazz> classes = classService.getClassesByTeacherId(teacherId);
            if(classes != null && !classes.isEmpty()){
                response.setMessage("该教师有班级，无法删除");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            Teacher teacher = teacherService.getTeacherById(teacherId);
            if(!Objects.equals(schoolAdmin.getSchoolId(), teacher.getSchoolId())){
                response.setMessage("该教师不是该学校教师");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            String schoolName = schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName();
            int result = teacherService.removeTeacher(teacherId);

            if(result != 0){
                response.setMessage("教师账号删除成功");
                if (teacher.getName() != null) {
                    operationLogger.info("{} 的管理员{} 删除了教师{}的账号", schoolName ,schoolAdmin.info(), teacher.info());
                }
                return ResponseEntity.ok(response);
            }else{
                response.setMessage("教师账号删除失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            logger.error("删除教师失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping ("/{id}/delete-student/{studentId}")
    public ResponseEntity<Message> deleteStudent(@PathVariable Long id, @PathVariable Long studentId) {
        try {
            Message response = new Message();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            String schoolName = schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName();
            Student student = studentService.getStudentById(studentId);
            if(!Objects.equals(schoolAdmin.getSchoolId(), student.getSchoolId())){
                response.setMessage("该学生不是该学校学生");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            ClassStudent classStudent = classStudentService.getClassStudentByStudentId(studentId);
            classService.removeStudentFromClass(classStudent.getClassId(), studentId);
            student.setSchoolId(null);
            studentService.updateStudent(student);
            if (student.getName() != null && schoolAdmin.getName() != null) {
                operationLogger.info("{} 的管理员{} 删除了学生{}的账号", schoolName ,schoolAdmin.info(), student.info());
            }
            response.setMessage("学生账号删除成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("删除学生失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/query-all-students")
    public ResponseEntity<SchoolAdminQueryStudents> queryAllStudents(@PathVariable Long id) {
        try {
            SchoolAdminQueryStudents response = new SchoolAdminQueryStudents();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            List<Student> students = studentService.getStudentsBySchoolId(schoolAdmin.getSchoolId());
            List<SchoolAdminQueryStudents.InfoData> data = new ArrayList<>();
            for(Student student : students){
                SchoolAdminQueryStudents.InfoData infoData = new SchoolAdminQueryStudents.InfoData();
                infoData.setId(student.getId());
                infoData.setName(student.getName());
                infoData.setUsername(student.getUsername());
                infoData.setEmail(student.getEmail());
                infoData.setGrade(student.getGrade());
                infoData.setSchoolId(student.getSchoolId());
                data.add(infoData);
            }
            response.setData(data);
            response.setMessage("全校学生信息查询成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("全校学生信息查询失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/query-all-teachers")
    public ResponseEntity<SchoolAdminQueryTeachers> queryAllTeachers(@PathVariable Long id) {
        try {
            SchoolAdminQueryTeachers response = new SchoolAdminQueryTeachers();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            List<Teacher> teachers = teacherService.getTeachersBySchoolId(schoolAdmin.getSchoolId());
            List<SchoolAdminQueryTeachers.InfoData> data = new ArrayList<>();
            for(Teacher teacher : teachers){
                SchoolAdminQueryTeachers.InfoData infoData = new SchoolAdminQueryTeachers.InfoData();
                infoData.setId(teacher.getId());
                infoData.setName(teacher.getName());
                infoData.setUsername(teacher.getUsername());
                infoData.setEmail(teacher.getEmail());
                infoData.setPhoneNumber(teacher.getPhoneNumber());
                infoData.setPermission(teacher.getPermission());
                infoData.setSchoolId(teacher.getSchoolId());
                data.add(infoData);
            }
            response.setData(data);
            response.setMessage("全校教师信息查询成功");
            String schoolName = schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName();
            operationLogger.info("{} 的管理员{} 查询全校教师信息", schoolName, schoolAdmin.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("全校教师信息查询失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/query-student/{studentId}")
    public ResponseEntity<SchoolAdminQueryStudent> queryStudent(@PathVariable Long id, @PathVariable Long studentId) {
        try {
            SchoolAdminQueryStudent response = new SchoolAdminQueryStudent();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            Student student = studentService.getStudentById(studentId);
            if(!Objects.equals(schoolAdmin.getSchoolId(), student.getSchoolId())){
                response.setMessage("该学生不是该学校学生");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            SchoolAdminQueryStudent.InfoData infoData = new SchoolAdminQueryStudent.InfoData();
            infoData.setId(student.getId());
            infoData.setName(student.getName());
            infoData.setUsername(student.getUsername());
            infoData.setEmail(student.getEmail());
            infoData.setGrade(student.getGrade());
            infoData.setSchoolId(student.getSchoolId());
            response.setData(infoData);
            response.setMessage("学生信息查询成功");
            String schoolName = schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName();
            operationLogger.info("{} 的管理员{} 查询学生{}信息", schoolName, schoolAdmin.info(), student.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("学生信息查询失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/query-teacher/{teacherId}")
    public ResponseEntity<SchoolAdminQueryTeacher> queryTeacher(@PathVariable Long id, @PathVariable Long teacherId) {
        try {
            SchoolAdminQueryTeacher response = new SchoolAdminQueryTeacher();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            if(!Objects.equals(schoolAdmin.getSchoolId(), teacher.getSchoolId())){
                response.setMessage("该教师不是该学校教师");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            SchoolAdminQueryTeacher.InfoData infoData = new SchoolAdminQueryTeacher.InfoData();
            infoData.setId(teacher.getId());
            infoData.setName(teacher.getName());
            infoData.setEmail(teacher.getEmail());
            infoData.setPhoneNumber(teacher.getPhoneNumber());
            infoData.setSchoolId(teacher.getSchoolId());
            response.setData(infoData);
            response.setMessage("教师信息查询成功");
            String schoolName = schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName();
            operationLogger.info("{} 的管理员{} 查询教师{}信息", schoolName, schoolAdmin.info(), teacher.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("教师信息查询失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("{id}/get-classes")
    public ResponseEntity<GetClassesResponse> getClasses(@PathVariable Long id) {
        try {
            GetClassesResponse response = new GetClassesResponse();
            List<GetClassesResponse.infoData> data = new ArrayList<>();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
            String schoolName = schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName();
            if(schoolAdmin.getSchoolId() != null){
                List<Clazz> classes = classService.selectBySchoolId(schoolAdmin.getSchoolId());
                for(Clazz clazz : classes){
                    GetClassesResponse.infoData infoData = new GetClassesResponse.infoData();
                    infoData.setClassId(clazz.getId());
                    infoData.setName(clazz.getName());
                    if(clazz.getCreatorId() != null){
                        Teacher teacher = teacherService.getTeacherById(clazz.getCreatorId());
                        infoData.setTeacherName(teacher.getName());
                    }
                    infoData.setInviteCode(clazz.getInviteCode());
                    data.add(infoData);
                }
            }
            response.setData(data);
            response.setMessage("班级列表获取成功");
            operationLogger.info("{} 的管理员{} 获取班级列表", schoolName, schoolAdmin.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("班级列表获取失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("{id}/query-class")
    public ResponseEntity<ClassDetailResponse> queryClass(@PathVariable Long id, @RequestParam Long classId) {
        try {
            ClassDetailResponse response = new ClassDetailResponse();
            ClassDetailResponse.infoData data = new ClassDetailResponse.infoData();
            List<ClassDetailResponse.GroupInfo> groups = new ArrayList<>();
            List<ClassDetailResponse.StudentInfo> students = new ArrayList<>();
            Clazz clazz = classService.getClassById(classId);
            if(clazz != null){
                data.setClassName(clazz.getName());
                data.setClassDescription(clazz.getDescription());
                List<ClassGroup> groupsByClassId = classGroupService.selectByClassId(classId);
                if(groupsByClassId != null){
                    for(ClassGroup group : groupsByClassId){
                        ClassDetailResponse.GroupInfo groupInfo = new ClassDetailResponse.GroupInfo();
                        groupInfo.setGroupId(group.getId());
                        groupInfo.setGroupName(group.getName());
                        groupInfo.setGroupDescription(group.getDescription());
                        groups.add(groupInfo);
                    }
                }
                List<ClassStudent> studentsByClassId = classStudentService.getClassStudentsByClassId(classId);
                if(studentsByClassId != null){
                    for(ClassStudent student : studentsByClassId){
                        ClassDetailResponse.StudentInfo studentInfo = new ClassDetailResponse.StudentInfo();
                        studentInfo.setStudentId(student.getStudentId());
                        studentInfo.setStudentName(studentService.getStudentById(student.getStudentId()).getName());
                        students.add(studentInfo);
                    }
                }
            }
            data.setGroups(groups);
            data.setStudents(students);
            response.setData(data);
            response.setMessage("班级详情获取成功");
            String schoolName = schoolService.getSchoolById(schoolAdminService.getSchoolAdminById(id).getSchoolId()).getName();
            operationLogger.info("{} 的管理员{} 查询班级{}详情", schoolName, schoolAdminService.getSchoolAdminById(id).info(), clazz.info());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("班级详情获取失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/class/knowledge-point-status")
    public ResponseEntity<ClassKnowledgePointStatusResponse> getClassKnowledgePointStatus(@RequestParam Long classId){
        ClassKnowledgePointStatusResponse response = new ClassKnowledgePointStatusResponse();
        List<ClassKnowledgePointStatusResponse.infoData> data = new ArrayList<>();
        List<StudentStatsView> studentStatsViews = studentStatsViewService.selectByClassId(classId);
        if(studentStatsViews != null && !studentStatsViews.isEmpty()){
            studentStatsViews.sort(Comparator.comparing(StudentStatsView::getType));
            String nameTemp = studentStatsViews.get(0).getType();
            int score = 0;
            int totalScore = 0;
            ClassKnowledgePointStatusResponse.infoData infoData;
            for(int i = 0; i < studentStatsViews.size(); i++){
                if(!Objects.equals(nameTemp, studentStatsViews.get(i).getType())){
                    infoData = new ClassKnowledgePointStatusResponse.infoData();
                    infoData.setName(nameTemp);
                    infoData.setScore(new BigDecimal(100 * score).divide(new BigDecimal(totalScore), 2, RoundingMode.HALF_UP));
                    data.add(infoData);
                    nameTemp = studentStatsViews.get(i).getType();
                    score = 0;
                    totalScore = 0;
                }
                score += studentStatsViews.get(i).getScore();
                totalScore += studentStatsViews.get(i).getTotalScore();
            }
            infoData = new ClassKnowledgePointStatusResponse.infoData();
            infoData.setName(nameTemp);
            infoData.setScore(new BigDecimal(100 * score).divide(new BigDecimal(totalScore), 2, RoundingMode.HALF_UP));
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-teachers-to-change")
    public ResponseEntity<GetTeachersToChangeResponse> getTeachersToChange(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user){
        try {
            GetTeachersToChangeResponse response = new GetTeachersToChangeResponse();
            List<GetTeachersToChangeResponse.infoData> data = new ArrayList<>();
            Long schoolAdminId = user.getId();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(schoolAdminId);
            List<Teacher> teachers = teacherService.getTeachersBySchoolId(schoolAdmin.getSchoolId());
            if(teachers != null && !teachers.isEmpty()){
                for(Teacher teacher : teachers){
                    GetTeachersToChangeResponse.infoData infoData = new GetTeachersToChangeResponse.infoData();
                    infoData.setTeacherId(teacher.getId());
                    infoData.setTeacherName(teacher.getName());
                    data.add(infoData);
                }
            }
            response.setData(data);
            response.setMessage("success");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            logger.error("获取老师失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/change-teacher-of-class")
    public ResponseEntity<Message> changeTeacherOfClass(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user,@RequestParam Long classId, @RequestParam Long teacherId){
        try {
            Message response = new Message();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(user.getId());
            Clazz clazz = classService.getClassById(classId);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            Teacher oldTeacher = teacherService.getTeacherById(clazz.getCreatorId());
            if(Objects.equals(schoolAdmin.getSchoolId(), teacher.getSchoolId()) && Objects.equals(schoolAdmin.getSchoolId(), oldTeacher.getSchoolId())){
                clazz.setCreatorId(teacherId);
                classService.updateClass(clazz);
                operationLogger.info("{} 的管理员{} 更改班级{} 老师为{}", schoolService.getSchoolById(schoolAdmin.getSchoolId()).getName(), schoolAdmin.info(), clazz.info(), teacher.info());
                response.setMessage("success");
                return ResponseEntity.ok(response);
            }
            else{
                response.setMessage("无权限");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }catch (Exception e){
            logger.error("更改班级老师失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/level-up")
    public ResponseEntity<Message> levelUpTeacher(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @RequestParam Long id){
        Message response = new Message();

        try {
            Teacher teacher = teacherService.getTeacherById(id);
            teacher.setPermission(Teacher.Leader);
            teacherService.updateTeacher(teacher);
            String schoolName = schoolService.getSchoolById(teacher.getSchoolId()).getName();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(user.getId());
            operationLogger.info("{} 的管理员{} 将 {} 升级为 组长,", schoolName, schoolAdmin.info(), teacher.info());
            response.setMessage("success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("升级老师失败，错误信息: {}", e.getMessage(), e);
            response.setMessage("fail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/level-down")
    public ResponseEntity<Message> levelDownTeacher(@AuthenticationPrincipal @Parameter(hidden = true) BaseUser user, @RequestParam Long id){
        Message response = new Message();

        try {
            Teacher teacher = teacherService.getTeacherById(id);
            teacher.setPermission(Teacher.TEACHER);
            teacherService.updateTeacher(teacher);
            String schoolName = schoolService.getSchoolById(teacher.getSchoolId()).getName();
            SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(user.getId());
            operationLogger.info("{} 的管理员{} 将 {} 降级为 普通老师,", schoolName, schoolAdmin.info(), teacher.info());
            response.setMessage("success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("降级老师失败，错误信息: {}", e.getMessage(), e);
            response.setMessage("fail");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}


package com.example.controller;

import com.example.dto.request.school.CreateManagerRequest;
import com.example.dto.response.*;
import com.example.dto.response.school.*;
import com.example.model.classes.ClassGroup;
import com.example.model.classes.ClassStudent;
import com.example.model.classes.Clazz;
import com.example.model.user.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/api/school-admin")
public class SchoolAdminBusinessController {
    private final SchoolAdminService schoolAdminService;
    private final ClassService classService;
    private final TeacherService teacherService;
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
                                         ClassGroupServiceImpl classGroupService
                                         ) {
        this.schoolAdminService = schoolAdminService;
        this.classService = classService;
        this.teacherService = teacherService;
        this.authorizationCodeService = authorizationCodeService;
        this.studentService = studentService;
        this.classStudentService = classStudentService;
        this.classGroupService = classGroupService;
    }

    @GetMapping("/{id}/generate-authorization-code")
    public ResponseEntity<GenerateAuthorizationCodeResponse> generateAuthorizationCode(@PathVariable Long id) {
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
                return ResponseEntity.ok(response);
            }
            response.setMessage("授权码创建失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    private static void extracted(StringBuilder code, String[] charsToCode, Random random) {
        for (int i = 0; i < 8; ++ i) {
            code.append(charsToCode[random.nextInt(36)]);
        }
    }

    @DeleteMapping("{id}/delete-teacher/{teacherId}")
    public ResponseEntity<Message> deleteTeacher(@PathVariable Long id, @PathVariable Long teacherId) {
        Message response = new Message();
        SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
        Teacher teacher = teacherService.getTeacherById(teacherId);
        if(!Objects.equals(schoolAdmin.getSchoolId(), teacher.getSchoolId())){
            response.setMessage("该教师不是该学校教师");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<Clazz> classes = classService.getClassesByTeacherId(teacherId);
        for (Clazz clazz : classes) {
            classService.removeClass(clazz.getId());
        }
        int result = teacherService.removeTeacher(teacherId);
        if(result != 0){
            response.setMessage("教师账号删除成功");
            return ResponseEntity.ok(response);
        }else{
            response.setMessage("教师账号删除失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping ("/{id}/delete-student/{studentId}")
    public ResponseEntity<Message> deleteStudent(@PathVariable Long id, @PathVariable Long studentId) {
        Message response = new Message();
        SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
        Student student = studentService.getStudentById(studentId);
        if(!Objects.equals(schoolAdmin.getSchoolId(), student.getSchoolId())){
            response.setMessage("该学生不是该学校学生");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<ClassStudent> classStudents = classStudentService.getClassStudentsByStudentId(studentId);
        if(!classStudents.isEmpty()){
            ClassStudent classStudent = classStudents.get(0);
            classService.removeStudentFromClass(classStudent.getClassId(), studentId);
        }
        student.setSchoolId(null);
        studentService.updateStudent(student);
        response.setMessage("学生账号删除成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/query-all-students")
    public ResponseEntity<SchoolAdminQueryStudents> queryAllStudents(@PathVariable Long id) {
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
    }

    @GetMapping("/{id}/query-all-teachers")
    public ResponseEntity<SchoolAdminQueryTeachers> queryAllTeachers(@PathVariable Long id) {
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
            infoData.setSchoolId(teacher.getSchoolId());
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("全校教师信息查询成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/query-student/{studentId}")
    public ResponseEntity<SchoolAdminQueryStudent> queryStudent(@PathVariable Long id, @PathVariable Long studentId) {
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
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/query-teacher/{teacherId}")
    public ResponseEntity<SchoolAdminQueryTeacher> queryTeacher(@PathVariable Long id, @PathVariable Long teacherId) {
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
        return ResponseEntity.ok(response);
    }



    @GetMapping("{id}/get-classes")
    public ResponseEntity<GetClassesResponse> getClasses(@PathVariable Long id) {
        GetClassesResponse response = new GetClassesResponse();
        List<GetClassesResponse.infoData> data = new ArrayList<>();
        SchoolAdmin schoolAdmin = schoolAdminService.getSchoolAdminById(id);
        if(schoolAdmin != null && schoolAdmin.getSchoolId() != null){
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
        return ResponseEntity.ok(response);
    }



    @GetMapping("{id}/query-class")
    public ResponseEntity<ClassDetailResponse> queryClass(@PathVariable Long id, @RequestParam Long classId) {
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
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-manager")
    public ResponseEntity<Message> createManager(@AuthenticationPrincipal BaseUser user, @RequestBody CreateManagerRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Teacher teacher = new Teacher();
        teacher.setPermission(1);
        teacher.setEmail(email);
        if (teacherService.existTeacher(teacher)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("邮箱已存在"));
        }

        Long schoolAdminId = user.getId();
        teacher.setSchoolId(schoolAdminService.getSchoolAdminById(schoolAdminId).getSchoolId());
        teacher.setPassword(password);

        teacherService.addTeacher(teacher);

        return ResponseEntity.ok(new Message("创建成功"));
    }
}


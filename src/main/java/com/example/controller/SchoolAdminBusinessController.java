package com.example.controller;

import com.example.dto.response.*;
import com.example.dto.response.school.*;
import com.example.model.classes.ClassStudent;
import com.example.model.classes.Clazz;
import com.example.model.user.AuthorizationCode;
import com.example.model.user.SchoolAdmin;
import com.example.model.user.Student;
import com.example.model.user.Teacher;
import com.example.service.classes.ClassService;
import com.example.service.classes.ClassStudentService;
import com.example.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    public SchoolAdminBusinessController(SchoolAdminService schoolAdminService,
                                         ClassService classService,
                                         TeacherService teacherService,
                                         AuthorizationCodeService authorizationCodeService,
                                         StudentService studentService,
                                         ClassStudentService classStudentService) {
        this.schoolAdminService = schoolAdminService;
        this.classService = classService;
        this.teacherService = teacherService;
        this.authorizationCodeService = authorizationCodeService;
        this.studentService = studentService;
        this.classStudentService = classStudentService;
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
        for (ClassStudent classStudent : classStudents) {
            classService.removeStudentFromClass(classStudent.getClassId(), studentId);
        }
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


}


package com.example.controller;


import com.example.dto.request.CreateSchoolAdminRequest;
import com.example.dto.response.Message;
import com.example.dto.response.QuestionsResponse;
import com.example.model.question.Question;
import com.example.model.user.SchoolAdmin;
import com.example.service.question.QuestionService;
import com.example.service.question.impl.QuestionServiceImpl;
import com.example.service.user.SchoolAdminService;
import com.example.service.user.impl.SchoolAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/system-admin")
public class SystemAdminBusinessController {
    private final SchoolAdminService schoolAdminService;
    private final QuestionService questionService;

    @Autowired
    public SystemAdminBusinessController(SchoolAdminServiceImpl schoolAdminService,
                                         QuestionServiceImpl questionService) {
        this.schoolAdminService = schoolAdminService;
        this.questionService = questionService;
    }

    @PostMapping("/create-school-admin")
    public ResponseEntity<Message> createSchoolAdmin(@RequestBody  CreateSchoolAdminRequest request) {
        String name = request.getName();
        String password = request.getPassword();
        Long schoolId = request.getSchoolId();
        Message response = new Message();
        if (schoolAdminService.checkExistSchool(name)) {
            response.setMessage("用户名已存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            SchoolAdmin schoolAdmin = new SchoolAdmin();
            schoolAdmin.setUsername(name);
            schoolAdmin.setPassword(password);
            schoolAdmin.setSchoolId(schoolId);
            schoolAdminService.addSchoolAdmin(schoolAdmin);
            response.setMessage("生成成功");
            return ResponseEntity.ok(response);
        } catch (Exception  e) {
            e.printStackTrace();
            response.setMessage("生成失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/get-all-questions")
    public ResponseEntity<QuestionsResponse> getAllQuestions() {
        QuestionsResponse response = new QuestionsResponse();
        response.setQuestions(new ArrayList<>());
        List<Question> questions = questionService.getAllQuestions();
        for (Question question : questions) {
            QuestionsResponse.questionInfo info = new QuestionsResponse.questionInfo();
            info.setId(question.getId());
            info.setDescription(question.getContent());

            //TODO:
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete-question/{id}")
    public ResponseEntity<Message> deleteQuestion(@PathVariable Long id) {
        try{
            Question question = questionService.getQuestionById(id);
            if (question == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("删除失败，未找到题目"));
            }
            questionService.deleteQuestion(id);
            return ResponseEntity.ok(new Message("删除成功"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("删除失败," + e.getMessage()));
        }
    }

}

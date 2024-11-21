package com.example.controller;

import com.example.dto.request.StudentBusinessController.CompletePracticeRequest;
import com.example.dto.request.StudentBusinessController.ContinuePracticeRequest;
import com.example.dto.request.StudentBusinessController.GeneratePracticeDefineRequest;
import com.example.dto.request.StudentBusinessController.SaveAnswerRequest;
import com.example.dto.response.Message;
import com.example.dto.response.StudentBusinessController.*;
import com.example.model.course.KnowledgePoint;
import com.example.model.question.Practice;
import com.example.model.question.PracticeQuestion;
import com.example.model.question.Question;
import com.example.model.submission.PracticeAnswer;
import com.example.service.course.KnowledgePointService;
import com.example.service.question.PracticeQuestionService;
import com.example.service.question.PracticeService;
import com.example.service.question.QuestionService;
import com.example.service.submission.PracticeAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/student")
public class StudentBusinessController {
    private final PracticeService practiceService;
    private final KnowledgePointService knowledgePointService;
    private final QuestionService questionService;
    private final PracticeQuestionService practiceQuestionService;
    private final PracticeAnswerService practiceAnswerService;

    @Autowired
    public StudentBusinessController (PracticeService practiceService,
                                      KnowledgePointService knowledgePointService,
                                      QuestionService questionService,
                                      PracticeQuestionService practiceQuestionService,
                                      PracticeAnswerService practiceAnswerService
                                      ) {
        this.practiceService = practiceService;
        this.knowledgePointService = knowledgePointService;
        this.questionService = questionService;
        this.practiceQuestionService = practiceQuestionService;
        this.practiceAnswerService = practiceAnswerService;
    }

    @GetMapping("/{id}/get-unfinished-practice-list")
    public ResponseEntity<GetUnfinishedPractices> getUnfinishedPractices(@PathVariable Long id) {
        GetUnfinishedPractices response = new GetUnfinishedPractices();
        List<Practice> practices = practiceService.getPracticesByStudentId(id);
        List<GetUnfinishedPractices.InfoData> data = new ArrayList<>();
        for(Practice practice : practices){
            if(practice.getPracticeTime() == null){
                GetUnfinishedPractices.InfoData infoData = new GetUnfinishedPractices.InfoData();
                infoData.setPracticeId(practice.getId());
                infoData.setPracticeName(practice.getName());
                data.add(infoData);
            }
        }
        response.setData(data);
        response.setMessage("未完成作业列表获取成功");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}/get-finished-practice-list")
    public ResponseEntity<GetFinishedPractices> getFinishedPractices(@PathVariable Long id) {
        GetFinishedPractices response = new GetFinishedPractices();
        List<Practice> practices = practiceService.getPracticesByStudentId(id);
        List<GetFinishedPractices.InfoData> data = new ArrayList<>();
        for(Practice practice : practices){
            if(practice.getPracticeTime() != null){
                GetFinishedPractices.InfoData infoData = new GetFinishedPractices.InfoData();
                infoData.setPracticeId(practice.getId());
                infoData.setPracticeName(practice.getName());
                infoData.setPracticeTime(practice.getPracticeTime().toString());
                infoData.setTotalScore(practice.getTotalScore().doubleValue());
                data.add(infoData);
            }
        }
        response.setData(data);
        response.setMessage("已完成作业列表获取成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/practice/get-knowledge-points")
    public ResponseEntity<GetKnowledgePointsResponse> getKnowledgePoints(@PathVariable Long id) {
        GetKnowledgePointsResponse response = new GetKnowledgePointsResponse();
        List<GetKnowledgePointsResponse.InfoData> data = new ArrayList<>();
        List<KnowledgePoint> knowledgePoints = knowledgePointService.getAllKnowledgePoints();
        for (KnowledgePoint knowledgePoint : knowledgePoints) {
            GetKnowledgePointsResponse.InfoData infoData = new GetKnowledgePointsResponse.InfoData();
            infoData.setId(knowledgePoint.getId());
            infoData.setName(knowledgePoint.getName());
            infoData.setDescription(knowledgePoint.getDescription());
            infoData.setType(knowledgePoint.getType());
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("知识点获取成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/practice/generate-define")
    public ResponseEntity<GeneratePracticeDefineResponse> generatePracticeDefine(@PathVariable Long id, @RequestBody GeneratePracticeDefineRequest request) {
        GeneratePracticeDefineResponse response = new GeneratePracticeDefineResponse();
        List<Question> questions = new ArrayList<>();
        for(GeneratePracticeDefineRequest.InfoData infoData : request.getData()){
            KnowledgePoint knowledgePoint = knowledgePointService.getKnowledgePointById(infoData.getKnowledgePointId());
            List<Question> questionsByKnowledgePoint = questionService.getQuestionsByKnowledgePointId(knowledgePoint.getId());
            questions.addAll(questionsByKnowledgePoint);
        }
        Practice practice = new Practice();
        practice.setStudentId(id);
        practice.setName(request.getName());
        practiceService.createPractice(practice);
        List<GeneratePracticeDefineResponse.InfoData> data = new ArrayList<>();
        for(int i = 0; i < request.getNum(); i++){
            int index = (int)(Math.random() * questions.size());
            Question question = questions.get(index);
            GeneratePracticeDefineResponse.InfoData infoData = new GeneratePracticeDefineResponse.InfoData();
            PracticeQuestion practiceQuestion = new PracticeQuestion();
            practiceQuestion.setPracticeId(practice.getId());
            practiceQuestion.setQuestionId(question.getId());
            practiceQuestion.setSequence(i + 1);
            practiceQuestionService.addPracticeQuestion(practiceQuestion);
            infoData.setPracticeQuestionId(practiceQuestion.getId());
            infoData.setQuestionContent(question.getContent());
            infoData.setType(question.getType());
            infoData.setQuestionOptions(question.getOptions());
            infoData.setSequence(i + 1);
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("练习生成成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/practice/save")
    public ResponseEntity<Message> saveAnswer(@PathVariable Long id, @RequestBody SaveAnswerRequest request) {
        Message message = new Message();
        for(SaveAnswerRequest.InfoData infoData : request.getData()){
            PracticeAnswer practiceAnswer = new PracticeAnswer();
            practiceAnswer.setPracticeQuestionId(infoData.getPracticeQuestionId());
            practiceAnswer.setAnswerContent(infoData.getAnswerContent());
            practiceAnswerService.addPracticeAnswer(practiceAnswer);
        }
        message.setMessage("答案保存成功");
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{id}/continue-practice")
    public ResponseEntity<ContinuePracticeResponse> continuePractice(@PathVariable Long id, @RequestBody ContinuePracticeRequest request) {
        ContinuePracticeResponse response = new ContinuePracticeResponse();
        List<PracticeQuestion> practiceQuestions = practiceQuestionService.getPracticeQuestionByPracticeId(request.getPracticeId());
        List<ContinuePracticeResponse.InfoData> data = new ArrayList<>();
        for(PracticeQuestion practiceQuestion : practiceQuestions){
            ContinuePracticeResponse.InfoData infoData = new ContinuePracticeResponse.InfoData();
            infoData.setPracticeQuestionId(practiceQuestion.getId());
            infoData.setSequence(practiceQuestion.getSequence());
            infoData.setQuestionContent(questionService.getQuestionById(practiceQuestion.getQuestionId()).getContent());
            infoData.setQuestionType(questionService.getQuestionById(practiceQuestion.getQuestionId()).getType());
            infoData.setQuestionOptions(questionService.getQuestionById(practiceQuestion.getQuestionId()).getOptions());
            infoData.setAnswerContent(practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId()).getAnswerContent());
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("获取成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/practice/complete")
    public ResponseEntity<CompletePracticeResponse> completePractice(@PathVariable Long id, @RequestBody CompletePracticeRequest request) {
        CompletePracticeResponse response = new CompletePracticeResponse();
        PracticeQuestion test = practiceQuestionService.getPracticeQuestionById(request.getData().get(0).getPracticeQuestionId());
        Practice practice = practiceService.getPracticeById(test.getPracticeId());
        double score = 0.0;
        double totalScore = 0.0;
        for(CompletePracticeRequest.InfoData infoData : request.getData()){
            PracticeQuestion practiceQuestion = practiceQuestionService.getPracticeQuestionById(infoData.getPracticeQuestionId());
            PracticeAnswer practiceAnswer = practiceAnswerService.getPracticeAnswerByPracticeQuestionId(infoData.getPracticeQuestionId());
            PracticeAnswer practiceAnswer1 = new PracticeAnswer();
            practiceAnswer1.setPracticeQuestionId(infoData.getPracticeQuestionId());
            practiceAnswer1.setAnswerContent(infoData.getAnswerContent());
            Question question = questionService.getQuestionById(practiceQuestion.getQuestionId());
            if(Objects.equals(question.getType(), "CHOICE")){
                if(question.getAnswer().equals(practiceAnswer.getAnswerContent())){
                    practiceAnswer1.setScore(BigDecimal.valueOf(1.0));
                    score += 1.0;
                    totalScore += 1.0;
                }
                else{
                    practiceAnswer1.setScore(BigDecimal.valueOf(0.0));
                    totalScore += 1.0;
                }
            }
            if(practiceAnswer == null){
                practiceAnswerService.addPracticeAnswer(practiceAnswer1);
            }
            else{
                practiceAnswerService.updatePracticeAnswer(practiceAnswer1);
            }
        }
        double finalScore;
        if(totalScore != 0){
            finalScore = score / totalScore;
            practice.setTotalScore(BigDecimal.valueOf(finalScore));
        }
        else{
            finalScore = 0.0;
            practice.setTotalScore(BigDecimal.valueOf(finalScore));
        }
        practiceService.updatePractice(practice);
        response.setScore(finalScore);
        response.setMessage("练习提交成功");
        return ResponseEntity.ok(response);
    }

}

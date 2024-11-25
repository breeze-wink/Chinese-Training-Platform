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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
            if(practice.getPracticeTime() == null ){
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
                infoData.setTotalScore(null);
                if(practice.getTotalScore() != null){
                    infoData.setTotalScore(practice.getTotalScore().doubleValue());
                }
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
    public ResponseEntity<GeneratePracticeDefineResponse> generatePracticeDefine(
        @PathVariable Long id, @RequestBody GeneratePracticeDefineRequest request) {
        GeneratePracticeDefineResponse response = new GeneratePracticeDefineResponse();
        List<Question> questions = new ArrayList<>();

        // 获取所有相关知识点的题目
        for (GeneratePracticeDefineRequest.InfoData infoData : request.getData()) {
            KnowledgePoint knowledgePoint = knowledgePointService.getKnowledgePointById(infoData.getKnowledgePointId());
            List<Question> questionsByKnowledgePoint = questionService.getQuestionsByKnowledgePointId(knowledgePoint.getId());
            questions.addAll(questionsByKnowledgePoint);
        }

        // 创建练习
        Practice practice = new Practice();
        practice.setStudentId(id);
        practice.setName(request.getName());
        practiceService.createPractice(practice);
        response.setPracticeId(practice.getId());

        // 初始化练习题目列表
        List<PracticeQuestion> practiceQuestions = new ArrayList<>();
        List<GeneratePracticeDefineResponse.InfoData> data = new ArrayList<>();

        // 随机选择题目并存储
        for (int i = 0; i < request.getNum(); i++) {
            int index = (int) (Math.random() * questions.size());
            Question question = questions.get(index);

            PracticeQuestion practiceQuestion = new PracticeQuestion();
            practiceQuestion.setPracticeId(practice.getId());
            practiceQuestion.setQuestionId(question.getId());

            // 设置初始的类型优先级
            if (Objects.equals(question.getType(), "CHOICE")) {
                practiceQuestion.setSequence(1);
            } else if (Objects.equals(question.getType(), "FILL_IN_BLANK")) {
                practiceQuestion.setSequence(2);
            } else if (Objects.equals(question.getType(), "SHORT_ANSWER")) {
                practiceQuestion.setSequence(3);
            }
            practiceQuestions.add(practiceQuestion);
        }

        // 按题目类型排序
        practiceQuestions.sort(Comparator.comparingInt(PracticeQuestion::getSequence));

        // 设置最终的序列值并保存
        int finalSequence = 1;
        for (PracticeQuestion practiceQuestion : practiceQuestions) {
            practiceQuestion.setSequence(finalSequence++);
            practiceQuestionService.addPracticeQuestion(practiceQuestion);

            // 构建返回数据
            Question question = questionService.getQuestionById(practiceQuestion.getQuestionId());
            GeneratePracticeDefineResponse.InfoData infoData = new GeneratePracticeDefineResponse.InfoData();
            infoData.setPracticeQuestionId(practiceQuestion.getId());
            infoData.setQuestionContent(question.getContent());
            infoData.setType(question.getType());
            infoData.setQuestionOptions(question.getOptions());
            infoData.setSequence(practiceQuestion.getSequence());
            data.add(infoData);
        }

        // 返回响应
        response.setData(data);
        response.setMessage("练习生成成功");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{id}/practice/generate-auto")//暂时
    public ResponseEntity<GeneratePracticeDefineResponse> generatePracticeAuto(@PathVariable Long id) {
        GeneratePracticeDefineResponse response = new GeneratePracticeDefineResponse();
        List<Question> questions = questionService.getAllQuestions();
        Practice practice = new Practice();
        practice.setStudentId(id);
        practiceService.createPractice(practice);
        List<GeneratePracticeDefineResponse.InfoData> data = new ArrayList<>();
        for(int i = 0; i < 5; i++){
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
            PracticeAnswer practiceAnswer = new PracticeAnswer();
            practiceAnswer.setPracticeQuestionId(infoData.getPracticeQuestionId());
            practiceAnswer.setAnswerContent(infoData.getAnswerContent());
            Question question = questionService.getQuestionById(practiceQuestion.getQuestionId());
            if(Objects.equals(question.getType(), "CHOICE")){
                if(question.getAnswer().equals(practiceAnswer.getAnswerContent())){
                    practiceAnswer.setScore(BigDecimal.valueOf(1.0));
                    score += 1.0;
                    totalScore += 1.0;
                }
                else{
                    practiceAnswer.setScore(BigDecimal.valueOf(0.0));
                    totalScore += 1.0;
                }
            }
            PracticeAnswer testPracticeAnswer = practiceAnswerService.getPracticeAnswerByPracticeQuestionId(infoData.getPracticeQuestionId());
            if(testPracticeAnswer == null){
                practiceAnswerService.addPracticeAnswer(practiceAnswer);
            }
            else{
                practiceAnswer.setId(testPracticeAnswer.getId());
                practiceAnswerService.updatePracticeAnswer(practiceAnswer);
            }
        }
        double finalScore;
        if(totalScore != 0){
            finalScore = score / totalScore * 100;
            practice.setTotalScore(BigDecimal.valueOf(finalScore));
        }
        else{
            finalScore = 0.0;
            practice.setTotalScore(BigDecimal.valueOf(finalScore));
        }
        practice.setPracticeTime(LocalDateTime.now());
        practiceService.updatePractice(practice);
        response.setScore(finalScore);
        response.setMessage("练习提交成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/practice/get-answer")
    public ResponseEntity<GetAnswerResponse> getAnswer(@PathVariable Long id, @RequestParam Long practiceId) {
        GetAnswerResponse response = new GetAnswerResponse();
        List<PracticeQuestion> practiceQuestions = practiceQuestionService.getPracticeQuestionByPracticeId(practiceId);
        List<GetAnswerResponse.InfoData> data = new ArrayList<>();
        for(PracticeQuestion practiceQuestion : practiceQuestions){
            GetAnswerResponse.InfoData infoData = new GetAnswerResponse.InfoData();
            infoData.setQuestionContent(questionService.getQuestionById(practiceQuestion.getQuestionId()).getContent());
            infoData.setQuestionType(questionService.getQuestionById(practiceQuestion.getQuestionId()).getType());
            if(Objects.equals(infoData.getQuestionType(), "CHOICE")){
                infoData.setQuestionOptions(questionService.getQuestionById(practiceQuestion.getQuestionId()).getOptions());
            }
            infoData.setAnswer(questionService.getQuestionById(practiceQuestion.getQuestionId()).getAnswer());
            infoData.setStudentAnswer(practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId()).getAnswerContent());
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("答案获取成功");
        return ResponseEntity.ok(response);
    }

}

package com.example.controller;

import com.example.dto.redis.PreAssembledQuestion;
import com.example.dto.redis.SubQuestion;
import com.example.dto.request.student.*;
import com.example.dto.response.Message;
import com.example.dto.response.student.*;
import com.example.dto.response.teacher.ClassKnowledgePointStatusResponse;
import com.example.model.classes.ClassStudent;
import com.example.model.classes.Clazz;
import com.example.model.classes.GroupStudent;
import com.example.model.course.KnowledgePoint;
import com.example.model.question.*;
import com.example.model.submission.AssignmentSubmission;
import com.example.model.submission.PracticeAnswer;
import com.example.model.submission.SubmissionAnswer;
import com.example.model.user.StatsStudent;
import com.example.model.user.Student;
import com.example.model.view.AssignmentIdStudentIdScore;
import com.example.model.view.StudentStatsView;
import com.example.service.classes.ClassGroupService;
import com.example.service.classes.ClassStudentService;
import com.example.service.classes.GroupStudentService;
import com.example.service.classes.impl.ClassGroupServiceImpl;
import com.example.service.classes.impl.ClassStudentServiceImpl;
import com.example.service.classes.impl.GroupStudentServiceImpl;
import com.example.service.course.KnowledgePointService;
import com.example.service.course.impl.KnowledgePointServiceImpl;
import com.example.service.question.*;
import com.example.service.question.impl.*;
import com.example.service.submission.AssignmentSubmissionService;
import com.example.service.submission.PracticeAnswerService;
import com.example.service.submission.SubmissionAnswerService;
import com.example.service.submission.impl.SubmissionAnswerServiceImpl;
import com.example.service.user.StudentService;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.view.AssignmentScoresViewService;
import com.example.service.view.StudentStatsViewService;
import com.example.service.view.impl.AssignmentScoresViewServiceImpl;
import com.example.service.view.impl.StudentStatsViewServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.service.submission.impl.AssignmentSubmissionServiceImpl;
import com.example.service.submission.impl.PracticeAnswerServiceImpl;
import com.example.service.user.StatsStudentService;
import com.example.service.user.impl.StatsStudentServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/student")
public class StudentBusinessController {
    private static final Logger logger = LoggerFactory.getLogger(StudentBusinessController.class);
    private static final Logger operationLogger = LoggerFactory.getLogger("operations.student");
    private final PracticeService practiceService;
    private final StudentService studentService;
    private final KnowledgePointService knowledgePointService;
    private final QuestionService questionService;
    private final PracticeQuestionService practiceQuestionService;
    private final PracticeAnswerService practiceAnswerService;
    private final PreAssembledQuestionService preAssembledQuestionService;
    private final QuestionBodyService questionBodyService;
    private final StatsStudentService statsStudentService;
    private final ClassStudentService classStudentService;
    private final AssignmentSubmissionService assignmentSubmissionService;
    private final GroupStudentService groupStudentService;
    private final AssignmentRecipientService assignmentRecipientService;
    private final AssignmentService assignmentService;
    private final PaperQuestionService paperQuestionService;
    private final SubmissionAnswerService submissionAnswerService;
    private final StudentStatsViewService studentStatsViewService;
    private final AssignmentScoresViewService assignmentScoresViewService;

    @Autowired
    public StudentBusinessController (PracticeServiceImpl practiceService,
                                      KnowledgePointServiceImpl knowledgePointService,
                                      QuestionServiceImpl questionService,
                                      PracticeQuestionServiceImpl practiceQuestionService,
                                      PracticeAnswerServiceImpl practiceAnswerService,
                                      QuestionBodyServiceImpl questionBodyService,
                                      PreAssembledQuestionServiceImpl preAssembledQuestionService,
                                      StatsStudentServiceImpl statsStudentService,
                                      ClassStudentServiceImpl classStudentService,
                                      AssignmentSubmissionServiceImpl assignmentSubmissionService,
                                      GroupStudentServiceImpl groupStudentService,
                                      AssignmentRecipientServiceImpl assignmentRecipientService,
                                      AssignmentServiceImpl assignmentService,
                                      PaperQuestionServiceImpl paperQuestionService,
                                      SubmissionAnswerServiceImpl submissionAnswerService,
                                      StudentServiceImpl studentService,
                                      StudentStatsViewServiceImpl studentStatsViewService,
                                      AssignmentScoresViewServiceImpl assignmentScoresViewService
                                      ) {
        this.practiceService = practiceService;
        this.knowledgePointService = knowledgePointService;
        this.questionService = questionService;
        this.practiceQuestionService = practiceQuestionService;
        this.practiceAnswerService = practiceAnswerService;
        this.questionBodyService = questionBodyService;
        this.preAssembledQuestionService = preAssembledQuestionService;
        this.statsStudentService = statsStudentService;
        this.classStudentService = classStudentService;
        this.assignmentSubmissionService = assignmentSubmissionService;
        this.groupStudentService = groupStudentService;
        this.assignmentRecipientService = assignmentRecipientService;
        this.assignmentService = assignmentService;
        this.paperQuestionService = paperQuestionService;
        this.submissionAnswerService = submissionAnswerService;
        this.studentService = studentService;
        this.studentStatsViewService = studentStatsViewService;
        this.assignmentScoresViewService = assignmentScoresViewService;
    }

    @GetMapping("/{id}/get-unfinished-practice-list")
    public ResponseEntity<GetUnfinishedPractices> getUnfinishedPractices(@PathVariable Long id) {
       try {
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
           Student student = studentService.getStudentById(id);
           operationLogger.info("学生{} 获取了作业列表", student.info());
           return ResponseEntity.ok(response);
       } catch (Exception e) {
           logger.error("获取未完成作业列表失败，错误信息: {}", e.getMessage(), e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
    }


    @GetMapping("/{id}/get-finished-practice-list")
    public ResponseEntity<GetFinishedPractices> getFinishedPractices(@PathVariable Long id) {
        try {
            GetFinishedPractices response = new GetFinishedPractices();
            List<Practice> practices = practiceService.getPracticesByStudentId(id);
            List<GetFinishedPractices.InfoData> data = new ArrayList<>();
            if(practices != null && !practices.isEmpty()){
                for(Practice practice : practices) {
                    if (practice.getPracticeTime() != null) {
                        GetFinishedPractices.InfoData infoData = new GetFinishedPractices.InfoData();
                        infoData.setPracticeId(practice.getId());
                        infoData.setPracticeName(practice.getName());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        infoData.setPracticeTime(practice.getPracticeTime().format(formatter));
                        infoData.setTotalScore(null);
                        if (practice.getTotalScore() != null) {
                            infoData.setTotalScore(practice.getTotalScore().doubleValue());
                        }
                        data.add(infoData);
                    }
                }
            }
            response.setData(data);
            response.setMessage("已完成练习列表获取成功");
            operationLogger.info("学生{} 获取了练习列表", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("获取已完成练习列表失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/practice/get-knowledge-points")
    public ResponseEntity<GetKnowledgePointsResponse> getKnowledgePoints(@PathVariable Long id) {
        try {
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
            operationLogger.info("学生{} 获取了所有知识点", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("获取知识点失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/practice/generate-define")
    public ResponseEntity<GeneratePracticeDefineResponse> generatePracticeDefine(
            @PathVariable Long id, @RequestBody GeneratePracticeDefineRequest request) {
        try {
            GeneratePracticeDefineResponse response = new GeneratePracticeDefineResponse();
            Practice practice = new Practice();
            practice.setStudentId(id);
            practice.setName(request.getName());
            practiceService.createPractice(practice);
            AtomicInteger questionIndex = new AtomicInteger(1);
            List<GeneratePracticeDefineResponse.InfoData> data = new ArrayList<>();
            List<PracticeQuestion> practiceQuestions = new ArrayList<>();

            // 优化1: 批量获取需要的所有知识点的题目
            if (request.getKnowledgePoints() != null && !request.getKnowledgePoints().isEmpty()) {
                List<Long> knowledgePointIds = request.getKnowledgePoints().stream()
                    .map(GeneratePracticeDefineRequest.KnowledgePoint::getKnowledgePointId)
                    .collect(Collectors.toList());

                // 假设已实现的批量获取题目方法
                List<Question> allQuestions = questionService.getQuestionsByKnowledgePointIds(knowledgePointIds);

                // 按知识点 ID 分组
                Map<Long, List<Question>> questionsByKnowledgePointId = allQuestions.stream()
                    .collect(Collectors.groupingBy(Question::getKnowledgePointId));


                for (GeneratePracticeDefineRequest.KnowledgePoint knowledgePoint : request.getKnowledgePoints()) {
                    List<Question> questions = questionsByKnowledgePointId.getOrDefault(knowledgePoint.getKnowledgePointId(), Collections.emptyList());
                    if (questions.isEmpty()) continue;

                    // 高效随机抽取
                    Collections.shuffle(questions);
                    List<Question> selectedQuestions = questions.stream().limit(knowledgePoint.getNum()).toList();

                    for (Question question : selectedQuestions) {
                        PracticeQuestion practiceQuestion = new PracticeQuestion();
                        practiceQuestion.setPracticeId(practice.getId());
                        practiceQuestion.setQuestionId(question.getId());
                        practiceQuestion.setSequence(getSequenceByQuestionType(question.getType()));
                        practiceQuestions.add(practiceQuestion);
                    }
                }
                //排序
                practiceQuestions.sort(Comparator.comparingInt(pq -> Integer.parseInt(pq.getSequence())));

                // 批量获取 Question 数据
                List<Long> questionIds = practiceQuestions.stream()
                    .map(PracticeQuestion::getQuestionId)
                    .collect(Collectors.toList());

                // 假设已实现的批量获取题目方法（从 Redis 缓存中获取）
                List<Question> questions = questionService.getQuestionsByIds(questionIds);

                // 批量获取 QuestionBody 数据
                Set<Long> questionBodyIds = questions.stream()
                    .map(Question::getBodyId)
                    .collect(Collectors.toSet());

                // 假设已实现的批量获取 QuestionBody 方法（从 Redis 缓存中获取）
                List<QuestionBody> questionBodies = questionBodyService.getQuestionBodiesByIds(new ArrayList<>(questionBodyIds));
                Map<Long, QuestionBody> questionBodyMap = questionBodies.stream()
                    .collect(Collectors.toMap(QuestionBody::getId, qb -> qb));

                practiceQuestionService.addPracticeQuestions(practiceQuestions);
                // 组装 InfoData
                for (PracticeQuestion practiceQuestion : practiceQuestions) {
                    GeneratePracticeDefineResponse.InfoData infoData = new GeneratePracticeDefineResponse.InfoData();
                    Question question = questions.stream()
                        .filter(q -> q.getId().equals(practiceQuestion.getQuestionId()))
                        .findFirst()
                        .orElse(null);

                    infoData.setQuestionBody(null);
                    QuestionBody questionBody = null;
                    if (question != null) {
                        questionBody = questionBodyMap.get(question.getBodyId());
                        infoData.setPracticeQuestionId(practiceQuestion.getId());
                        if (questionBody != null) {
                            infoData.setQuestionBody(questionBody.getBody());
                        }
                        infoData.setQuestionContent(question.getContent());
                        infoData.setType(question.getType());
                        if (Objects.equals(infoData.getType(), "CHOICE")) {
                            infoData.setQuestionOptions(drawOptions(question.getOptions()));
                        }
                    }

                    String sequence = String.valueOf(questionIndex.getAndIncrement());
                    infoData.setSequence(sequence);
                    practiceQuestion.setSequence(sequence);
                    infoData.setPracticeQuestionId(practiceQuestion.getId());
                    data.add(infoData);
                }
            }

            // 优化5: 使用 Redis 缓存预组装的题目
            if (request.getQuestionBodyTypes() != null && !request.getQuestionBodyTypes().isEmpty()) {
                List<String> types = request.getQuestionBodyTypes().stream()
                    .map(GeneratePracticeDefineRequest.QuestionBodyType::getType)
                    .collect(Collectors.toList());

                // 从 Redis 获取预组装的题目列表
                List<PreAssembledQuestion> preassembledQuestions = preAssembledQuestionService.getPreAssembledQuestionsByTypes(types);
                List<PreAssembledQuestion> finalBigQuestions = new ArrayList<>();
                for (GeneratePracticeDefineRequest.QuestionBodyType questionBodyType : request.getQuestionBodyTypes()) {
                    //取出当前类型
                    List<PreAssembledQuestion> typeQuestions = preassembledQuestions.stream()
                        .filter(q -> q.getType().equals(questionBodyType.getType()))
                        .collect(Collectors.toList());

                    if (typeQuestions.isEmpty()) continue;

                    // 高效随机抽取
                    Collections.shuffle(typeQuestions);
                    List<PreAssembledQuestion> selectedTypeQuestions = typeQuestions.stream()
                        .limit(questionBodyType.getNum())
                        .toList();
                    finalBigQuestions.addAll(selectedTypeQuestions);
                }
                for (PreAssembledQuestion preassembledQuestion : finalBigQuestions) {
                    // 组装 InfoData 和 PracticeQuestion
                    // 先创建 PracticeQuestion 实体列表

                    String sequence = String.valueOf(questionIndex.getAndIncrement());
                    int index = 1;
                    for (SubQuestion subQ : preassembledQuestion.getSubQuestions()){
                        PracticeQuestion pq = new PracticeQuestion();
                        GeneratePracticeDefineResponse.InfoData infoData = new GeneratePracticeDefineResponse.InfoData();
                        if (index == 1) {
                            infoData.setQuestionBody(preassembledQuestion.getQuestionBody());
                        }
                        pq.setPracticeId(practice.getId());
                        pq.setQuestionId(subQ.getQuestionId());
                        pq.setSequence(sequence + "." + index);
                        infoData.setSequence(sequence + "." + index++);

                        practiceQuestionService.addPracticeQuestion(pq);

                        infoData.setQuestionContent(subQ.getQuestionContent());
                        infoData.setType(subQ.getType());
                        infoData.setPracticeQuestionId(pq.getId());
                        if ("CHOICE".equals(infoData.getType())) {
                            infoData.setQuestionOptions(drawOptions(subQ.getQuestionOptions()));
                        }
                        data.add(infoData);
                    }

                }
            }

            response.setPracticeId(practice.getId());
            response.setData(data);
            response.setMessage("练习生成成功");
            Student student = studentService.getStudentById(id);
            operationLogger.info("学生{}生成了自定义练习", student.info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("自定义练习生成失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 辅助方法：根据题目类型获取序号
    private String getSequenceByQuestionType(String type) {
        return switch (type) {
            case "CHOICE" -> "1";
            case "FILL_IN_BLANK" -> "2";
            case "SHORT_ANSWER" -> "3";
            case "ESSAY" -> "9";
            default -> "0";
        };
    }




    @PostMapping("/{id}/practice/generate-auto")
    public ResponseEntity<GeneratePracticeDefineResponse> generatePracticeAuto(@PathVariable Long id, @RequestParam String practiceName) {
        try {
            GeneratePracticeDefineResponse response = new GeneratePracticeDefineResponse();
            List<GeneratePracticeDefineResponse.InfoData> data = new ArrayList<>();
            List<PracticeQuestion> practiceQuestions = new ArrayList<>();
            final int QUESTION_NUM = 30;
            AtomicInteger questionIndex = new AtomicInteger(1);
            Practice practice = new Practice();
            practice.setName(practiceName);
            practice.setStudentId(id);
            practiceService.createPractice(practice);
            List<StatsStudent> statsStudents = statsStudentService.getStatsStudentByStudentId(id);
            List<KnowledgePoint> knowledgePoints = new ArrayList<>();
            if(statsStudents != null && !statsStudents.isEmpty()){
                for(int i = 0; i < statsStudents.size() && i < 5; i++){
                    if(statsStudents.get(i).getTotalScore() == null || statsStudents.get(i).getTotalScore() == 0){
                        statsStudents.remove(i);
                        i--;
                    }
                }
                if(!statsStudents.isEmpty()){
                    for(StatsStudent statsStudent : statsStudents){
                        statsStudent.setScore(1000 * statsStudent.getScore() / statsStudent.getTotalScore());
                    }
                    statsStudents.sort(Comparator.comparingLong(StatsStudent::getScore));
                    for(StatsStudent statsStudent : statsStudents){
                        knowledgePoints.add(knowledgePointService.getKnowledgePointById(statsStudent.getKnowledgePointId()));
                    }
                }
            }
            if(knowledgePoints.isEmpty()){
                knowledgePoints = knowledgePointService.getAllKnowledgePoints();
            }
            List<Long> knowledgePointIds = knowledgePoints.stream()
                .filter(Objects::nonNull)
                .map(KnowledgePoint::getId)
                .collect(Collectors.toList());

            // 假设已实现的批量获取题目方法
            List<Question> allQuestions = questionService.getQuestionsByKnowledgePointIds(knowledgePointIds);

            // 高效随机抽取
            Collections.shuffle(allQuestions);
            List<Question> questions = allQuestions.stream().limit(QUESTION_NUM).toList();

            for (Question question : questions) {
                PracticeQuestion practiceQuestion = new PracticeQuestion();
                practiceQuestion.setPracticeId(practice.getId());
                practiceQuestion.setQuestionId(question.getId());
                practiceQuestion.setSequence(getSequenceByQuestionType(question.getType()));
                practiceQuestions.add(practiceQuestion);
            }
            //排序
            practiceQuestions.sort(Comparator.comparingInt(pq -> Integer.parseInt(pq.getSequence())));

            // 批量获取 QuestionBody 数据
            Set<Long> questionBodyIds = questions.stream()
                .map(Question::getBodyId)
                .collect(Collectors.toSet());

            // 假设已实现的批量获取 QuestionBody 方法（从 Redis 缓存中获取）
            List<QuestionBody> questionBodies = questionBodyService.getQuestionBodiesByIds(new ArrayList<>(questionBodyIds));
            Map<Long, QuestionBody> questionBodyMap = questionBodies.stream()
                .collect(Collectors.toMap(QuestionBody::getId, qb -> qb));

            practiceQuestionService.addPracticeQuestions(practiceQuestions);
            // 组装 InfoData
            for (PracticeQuestion practiceQuestion : practiceQuestions) {
                GeneratePracticeDefineResponse.InfoData infoData = new GeneratePracticeDefineResponse.InfoData();
                Question question = questions.stream()
                    .filter(q -> q.getId().equals(practiceQuestion.getQuestionId()))
                    .findFirst()
                    .orElse(null);

                infoData.setQuestionBody(null);
                QuestionBody questionBody = null;
                if (question != null) {
                    questionBody = questionBodyMap.get(question.getBodyId());
                    infoData.setPracticeQuestionId(practiceQuestion.getId());
                    if (questionBody != null) {
                        infoData.setQuestionBody(questionBody.getBody());
                    }
                    infoData.setQuestionContent(question.getContent());
                    infoData.setType(question.getType());
                    if (Objects.equals(infoData.getType(), "CHOICE")) {
                        infoData.setQuestionOptions(drawOptions(question.getOptions()));
                    }
                }
                String sequence = String.valueOf(questionIndex.getAndIncrement());
                infoData.setSequence(sequence);
                practiceQuestion.setSequence(sequence);
                infoData.setPracticeQuestionId(practiceQuestion.getId());
                data.add(infoData);
            }
            response.setPracticeId(practice.getId());
            response.setData(data);
            response.setMessage("练习生成成功");
            Student student = studentService.getStudentById(id);
            operationLogger.info("学生{}生成了自动练习", student.info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("自动练习生成失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private static List<String> drawOptions(String optionString) {
        List<String> choices = new ArrayList<>(List.of(optionString.split("\\$\\$")));
        char choiceOption = 'A';
        for(int j = 0; j < choices.size(); j++){
            String [] test = choices.get(j).split("\\.");
            if(choiceOption != test[0].charAt(0)){
                choices.set(j, choiceOption + "." + choices.get(j));
            }
            choiceOption++;
        }
        return choices;
    }



    @PostMapping("/{id}/practice/save")
    public ResponseEntity<Message> saveAnswer(@PathVariable Long id, @RequestBody SaveAnswerRequest request) {
        try {
            Message message = new Message();
            for(SaveAnswerRequest.InfoData infoData : request.getData()){
                PracticeAnswer practiceAnswer = practiceAnswerService.getPracticeAnswerByPracticeQuestionId(infoData.getPracticeQuestionId());
                if(practiceAnswer != null){
                    practiceAnswer.setAnswerContent(infoData.getAnswerContent());
                    practiceAnswerService.updatePracticeAnswer(practiceAnswer);
                }
                else{
                    practiceAnswer = new PracticeAnswer();
                    practiceAnswer.setAnswerContent(infoData.getAnswerContent());
                    practiceAnswer.setPracticeQuestionId(infoData.getPracticeQuestionId());
                    practiceAnswerService.addPracticeAnswer(practiceAnswer);
                }
            }
            message.setMessage("答案保存成功");
            operationLogger.info("学生{}保存了练习答案", studentService.getStudentById(id).info());
            return ResponseEntity.ok(message);
        } catch (Exception e){
            logger.error("练习答案保存失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/continue-practice")
    public ResponseEntity<ContinuePracticeResponse> continuePractice(@PathVariable Long id, @RequestBody ContinuePracticeRequest request) throws JsonProcessingException {
        try {
            ContinuePracticeResponse response = new ContinuePracticeResponse();
            List<PracticeQuestion> practiceQuestions = practiceQuestionService.getPracticeQuestionByPracticeId(request.getPracticeId());
            List<ContinuePracticeResponse.InfoData> data = new ArrayList<>();
            for(PracticeQuestion practiceQuestion : practiceQuestions){
                ContinuePracticeResponse.InfoData infoData = new ContinuePracticeResponse.InfoData();
                infoData.setPracticeQuestionId(practiceQuestion.getId());
                infoData.setSequence(practiceQuestion.getSequence());
                infoData.setQuestionContent(questionService.getQuestionById(practiceQuestion.getQuestionId()).getContent());
                infoData.setQuestionType(questionService.getQuestionById(practiceQuestion.getQuestionId()).getType());
                infoData.setQuestionOptions(new ArrayList<>());
                if(Objects.equals(infoData.getQuestionType(), "CHOICE")){
                    Question question = questionService.getQuestionById(practiceQuestion.getQuestionId());
                    List<String> choices = drawOptions(question.getOptions());
                    infoData.getQuestionOptions().addAll(choices);
                }
                PracticeAnswer practiceAnswer = practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId());
                if(practiceAnswer != null){
                    infoData.setAnswerContent(practiceAnswer.getAnswerContent());
                }
                String [] sequences = infoData.getSequence().split("\\.");
                infoData.setQuestionBody("");
                if(sequences.length > 1){
                    if(sequences[1].equals("1")){
                        infoData.setQuestionBody(questionBodyService.getQuestionBodyById(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId()).getBody());
                    }
                }
                else if(sequences.length == 1){
                    if(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId() != null){
                        infoData.setQuestionBody(questionBodyService.getQuestionBodyById(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId()).getBody());
                    }
                }
                data.add(infoData);
            }
            response.setData(data);
            response.setMessage("获取成功");
            operationLogger.info("学生{}继续练习", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("继续练习失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/{id}/practice/complete")
    public ResponseEntity<CompletePracticeResponse> completePractice(@PathVariable Long id, @RequestBody CompletePracticeRequest request) throws JsonProcessingException {
        try {
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
                    String [] answerArray = question.getAnswer().split("\\$\\$");
                    if(answerArray[0].equals(practiceAnswer.getAnswerContent())){
                        practiceAnswer.setScore(BigDecimal.valueOf(5.0));
                        score += 5.0;
                        totalScore += 5.0;
                    }
                    else{
                        practiceAnswer.setScore(BigDecimal.valueOf(0.0));
                        totalScore += 5.0;
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
            operationLogger.info("学生{}提交练习", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("提交练习失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/practice/get-answer")
    public ResponseEntity<GetAnswerResponse> getAnswer(@PathVariable Long id, @RequestParam Long practiceId) throws JsonProcessingException {
        try {
            GetAnswerResponse response = new GetAnswerResponse();
            response.setTotalScore(practiceService.getPracticeById(practiceId).getTotalScore().doubleValue());
            List<PracticeQuestion> practiceQuestions = practiceQuestionService.getPracticeQuestionByPracticeId(practiceId);
            List<GetAnswerResponse.InfoData> data = new ArrayList<>();
            for(PracticeQuestion practiceQuestion : practiceQuestions){
                GetAnswerResponse.InfoData infoData = new GetAnswerResponse.InfoData();
                infoData.setQuestionContent(questionService.getQuestionById(practiceQuestion.getQuestionId()).getContent());
                infoData.setQuestionType(questionService.getQuestionById(practiceQuestion.getQuestionId()).getType());
                infoData.setSequence(practiceQuestion.getSequence());
                infoData.setScore(null);
                infoData.setQuestionOptions(new ArrayList<>());
                if(Objects.equals(infoData.getQuestionType(), "CHOICE")){
                    Question question = questionService.getQuestionById(practiceQuestion.getQuestionId());
                    List<String> answerArray = drawOptions(question.getOptions());
                    infoData.setQuestionOptions(answerArray);
                    PracticeAnswer practiceAnswer = practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId());
                    if(practiceAnswer != null && practiceAnswer.getScore() != null){
                        infoData.setScore(practiceAnswer.getScore().doubleValue());
                    }
                }
                String [] answerAndAnalysis = questionService.getQuestionById(practiceQuestion.getQuestionId()).getAnswer().split("\\$\\$");
                String replace = answerAndAnalysis[0].replace("##", ";");
                infoData.setAnswer(replace);
                infoData.setStudentAnswer(practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId()).getAnswerContent());
                infoData.setAnalysis("");
                if(answerAndAnalysis.length > 1){
                    infoData.setAnalysis(answerAndAnalysis[1]);
                }
                infoData.setQuestionBody("");
                String [] sequenceArray = practiceQuestion.getSequence().split("\\.");
                if(sequenceArray.length > 1){
                    if(sequenceArray[1].equals("1")){
                        infoData.setQuestionBody(questionBodyService.getQuestionBodyById(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId()).getBody());
                    }
                }
                else if(sequenceArray.length == 1){
                    if(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId() != null){
                        infoData.setQuestionBody(questionBodyService.getQuestionBodyById(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId()).getBody());
                    }
                }
                data.add(infoData);
            }
            response.setData(data);
            response.setMessage("答案获取成功");
            operationLogger.info("学生{}获取练习答案，练习id为{}", studentService.getStudentById(id).info(), practiceId);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取练习答案失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @DeleteMapping("/{id}/delete-practice")
    public ResponseEntity<Message> deletePractice(@PathVariable Long id, @RequestParam Long practiceId) {
        try {
            Message response = new Message();
            Practice practice = practiceService.getPracticeById(practiceId);
            if(practice == null){
                response.setMessage("练习不存在");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            else if(!Objects.equals(practice.getStudentId(), id)){
                response.setMessage("id不匹配，练习删除失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            List<PracticeQuestion> practiceQuestions = practiceQuestionService.getPracticeQuestionByPracticeId(practiceId);
            if(practiceQuestions != null){
                for(PracticeQuestion practiceQuestion : practiceQuestions){
                    PracticeAnswer practiceAnswer = practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId());
                    if(practiceAnswer != null){
                        practiceAnswerService.deletePracticeAnswer(practiceAnswer.getId());
                    }
                    practiceQuestionService.deletePracticeQuestion(practiceQuestion.getId());
                }
            }
            practiceService.deletePractice(practiceId);
            response.setMessage("练习删除成功");
            operationLogger.info("学生{}删除练习", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生删除练习失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("{id}/get-avg-score")
    public ResponseEntity<AvgScoreResponse> getAvgScore(@PathVariable Long id) {
        try {
            AvgScoreResponse response = new AvgScoreResponse();
            AvgScoreResponse.infoData data = new AvgScoreResponse.infoData();
            data.setAverageHomeworkScore(null);
            data.setClassRank(null);
            List<StatsStudent> statsStudents = statsStudentService.getStatsStudentByStudentId(id);
            if(statsStudents != null && (!statsStudents.isEmpty())){
                long totalScore = 0L;
                long score = 0L;
                for(StatsStudent statsStudent : statsStudents){
                    if(statsStudent.getTotalScore() != null){
                        totalScore += statsStudent.getTotalScore();
                    }
                    if(statsStudent.getScore() != null){
                        score += statsStudent.getScore();
                    }
                }
                double averageHomeworkScore = 100 * (double) score / (double) totalScore;
                averageHomeworkScore = Double.parseDouble(String.format("%.2f", averageHomeworkScore));
                data.setAverageHomeworkScore(averageHomeworkScore);
            }
            ClassStudent classStudent = classStudentService.getClassStudentByStudentId(id);
            if(classStudent != null){
                List<ClassStudent> classStudents = classStudentService.getClassStudentsByClassId(classStudent.getClassId());
                long[][] stats = new long[classStudents.size()][2];
                for(int i = 0; i < classStudents.size(); i++){
                    stats[i][0] = classStudents.get(i).getStudentId();
                    long totalScore = 0L;
                    long score = 0L;
                    List<StatsStudent> statsStudentsTemp = statsStudentService.getStatsStudentByStudentId(classStudents.get(i).getStudentId());
                    for(StatsStudent statsStudent : statsStudentsTemp){
                        if(statsStudent.getTotalScore() != null){
                            totalScore += statsStudent.getTotalScore();
                        }
                        if(statsStudent.getScore() != null){
                            score += statsStudent.getScore();
                        }
                    }
                    stats[i][1] = 0L;
                    if(totalScore != 0){
                        stats[i][1] = (long)(1000 * (double) score / (double) totalScore);
                    }
                }
                Arrays.sort(stats, Comparator.comparingLong(o -> o[1]));
                for(int i = 0; i <stats.length ; i++){
                    if(stats[i][0] == id){
                        data.setClassRank((long)(stats.length - i));
                        break;
                    }
                }
            }
            response.setData(data);
            response.setMessage("平均分获取成功");
            operationLogger.info("学生{}获取平均分", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取平均分失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("{id}/get-multidimensional-scores")
    public ResponseEntity<MultidimensionalScoresResponse> getMultidimensionalScores(@PathVariable Long id) {
        try {
            MultidimensionalScoresResponse response = new MultidimensionalScoresResponse();
            List<MultidimensionalScoresResponse.infoData> data = new ArrayList<>();
            List<StudentStatsView> studentStatsViews = studentStatsViewService.selectByStudentId(id);
            if(studentStatsViews != null && !studentStatsViews.isEmpty()){
                studentStatsViews.sort(Comparator.comparing(StudentStatsView::getType));
                String nameTemp = studentStatsViews.get(0).getType();
                Double score = 0.0;
                Double totalScore = 0.0;
                MultidimensionalScoresResponse.infoData infoData;
                for(int i = 0; i < studentStatsViews.size(); i++){
                    if(!Objects.equals(nameTemp, studentStatsViews.get(i).getType())){
                        infoData = new MultidimensionalScoresResponse.infoData();
                        infoData.setName(nameTemp);
                        infoData.setScore(Double.parseDouble(String.format("%.2f", 100 * score / totalScore)));
                        data.add(infoData);
                        nameTemp = studentStatsViews.get(i).getType();
                        score = 0.0;
                        totalScore = 0.0;
                    }
                    score += studentStatsViews.get(i).getScore();
                    totalScore += studentStatsViews.get(i).getTotalScore();
                }
                infoData = new MultidimensionalScoresResponse.infoData();
                infoData.setName(nameTemp);
                infoData.setScore(Double.parseDouble(String.format("%.2f", 100 * score / totalScore)));
                data.add(infoData);
            }
            response.setData(data);
            response.setMessage("各项成绩获取成功");
            operationLogger.info("学生{}获取各项成绩", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取各项成绩失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("{id}/get-weakness-scores")
    public ResponseEntity<WeaknessScoresResponse> getWeaknessScores(@PathVariable Long id) {
        try {
            WeaknessScoresResponse response = new WeaknessScoresResponse();
            List<WeaknessScoresResponse.infoData> data = new ArrayList<>();
            List<StudentStatsView> studentStatsViews = studentStatsViewService.selectByStudentId(id);
            if(studentStatsViews != null && !studentStatsViews.isEmpty()){
                studentStatsViews.sort(Comparator.comparing(StudentStatsView::getType));
                String nameTemp = studentStatsViews.get(0).getType();
                Long knowledgePointIdTemp = studentStatsViews.get(0).getKnowledgePointId();
                double avgScore = 1.0;
                WeaknessScoresResponse.infoData infoData;
                for(int i = 0; i < studentStatsViews.size(); i++){
                    if(!Objects.equals(nameTemp, studentStatsViews.get(i).getType())){
                        infoData = new WeaknessScoresResponse.infoData();
                        infoData.setType(nameTemp);
                        infoData.setWeaknessName(knowledgePointService.getKnowledgePointById(knowledgePointIdTemp).getName());
                        infoData.setWeaknessScore(Double.parseDouble(String.format("%.2f", 100 * avgScore)));
                        data.add(infoData);
                        nameTemp = studentStatsViews.get(i).getType();
                        knowledgePointIdTemp = studentStatsViews.get(i).getKnowledgePointId();
                        avgScore = 1.0;
                    }
                    StatsStudent statsStudent = statsStudentService.selectByStudentIdAndKnowledgePointId(id, studentStatsViews.get(i).getKnowledgePointId());
                    double scoreTemp = (double) statsStudent.getScore() / (double) statsStudent.getTotalScore();
                    if(scoreTemp < avgScore){
                        knowledgePointIdTemp = studentStatsViews.get(i).getKnowledgePointId();
                        avgScore = scoreTemp;
                    }
                }
                infoData = new WeaknessScoresResponse.infoData();
                infoData.setType(nameTemp);
                infoData.setWeaknessName(knowledgePointService.getKnowledgePointById(knowledgePointIdTemp).getName());
                infoData.setWeaknessScore(Double.parseDouble(String.format("%.2f", 100 * avgScore)));
                data.add(infoData);
            }
            response.setData(data);
            response.setMessage("短板获取成功");
            operationLogger.info("学生{}获取短板", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取短板失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("{id}/score-fluctuations")
    public ResponseEntity<HistoryScoresResponse> getHistoryScores(@PathVariable Long id) {
        try {
            HistoryScoresResponse response = new HistoryScoresResponse();
            List<HistoryScoresResponse.infoData> data = new ArrayList<>();
            List<AssignmentIdStudentIdScore> assignmentIdStudentIdScores = assignmentScoresViewService.selectScoresByStudentId(id);
            if(assignmentIdStudentIdScores != null && !assignmentIdStudentIdScores.isEmpty()){
                for(AssignmentIdStudentIdScore assignmentIdStudentIdScore : assignmentIdStudentIdScores){
                    if(assignmentIdStudentIdScore.getScore() != null){
                        HistoryScoresResponse.infoData infoData = new HistoryScoresResponse.infoData();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        infoData.setDate(assignmentService.selectById(assignmentIdStudentIdScore.getAssignmentId()).getEndTime().format(formatter));
                        infoData.setScore(assignmentIdStudentIdScore.getScore());
                        data.add(infoData);
                    }
                }
            }
            data.sort(Comparator.comparing(HistoryScoresResponse.infoData::getDate).reversed());
            response.setData(data);
            response.setMessage("历史成绩获取成功");
            operationLogger.info("学生{}获取历史成绩", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取历史成绩失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("{id}/get-unfinished-assignment-list")
    public ResponseEntity<UnfinishedAssignmentResponse> getUnfinishedAssignments(@PathVariable Long id) {
        try {
            UnfinishedAssignmentResponse response = new UnfinishedAssignmentResponse();
            List<UnfinishedAssignmentResponse.infoData> data = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            List<AssignmentRecipient> assignmentsByStudentId = assignmentRecipientService.selectByRecipient("STUDENT", id);
            if(assignmentsByStudentId != null){
                for(AssignmentRecipient assignment : assignmentsByStudentId){
                    Assignment assignmentTemp = assignmentService.selectById(assignment.getAssignmentId());
                    if(assignmentTemp.getEndTime().isBefore(now)){
                        continue;
                    }
                    UnfinishedAssignmentResponse.infoData infoData = new UnfinishedAssignmentResponse.infoData();
                    infoData.setAssignmentId(assignmentTemp.getId());
                    infoData.setTitle(assignmentTemp.getTitle());
                    if(assignmentTemp.getDescription() != null){
                        infoData.setDescription(assignmentTemp.getDescription());
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    infoData.setStartTime(assignmentTemp.getStartTime().format(formatter));
                    infoData.setEndTime(assignmentTemp.getEndTime().format(formatter));
                    data.add(infoData);
                }
            }
            ClassStudent classStudent = classStudentService.getClassStudentByStudentId(id);
            if(classStudent != null){
                Long classId = classStudent.getClassId();
                if(classId != null){
                    List<AssignmentRecipient> assignmentsByClassId = assignmentRecipientService.selectByRecipient("CLASS", classId);
                    if(assignmentsByClassId != null){
                        for(AssignmentRecipient assignment : assignmentsByClassId){
                            Assignment assignmentTemp = assignmentService.selectById(assignment.getAssignmentId());
                            if(assignmentTemp.getEndTime().isBefore(now)){
                                continue;
                            }
                            UnfinishedAssignmentResponse.infoData infoData = new UnfinishedAssignmentResponse.infoData();
                            infoData.setAssignmentId(assignmentTemp.getId());
                            infoData.setTitle(assignmentTemp.getTitle());
                            if(assignmentTemp.getDescription() != null){
                                infoData.setDescription(assignmentTemp.getDescription());
                            }
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            infoData.setStartTime(assignmentTemp.getStartTime().format(formatter));
                            infoData.setEndTime(assignmentTemp.getEndTime().format(formatter));
                            data.add(infoData);
                        }
                    }
                    List<GroupStudent> groupStudents = groupStudentService.getGroupStudentsByStudentId(id);
                    if(groupStudents != null){
                        for(GroupStudent groupStudent : groupStudents){
                            List<AssignmentRecipient> assignmentsByGroupId = assignmentRecipientService.selectByRecipient("GROUP", groupStudent.getGroupId());
                            if(assignmentsByGroupId != null){
                                for(AssignmentRecipient assignment : assignmentsByGroupId){
                                    Assignment assignmentTemp = assignmentService.selectById(assignment.getAssignmentId());
                                    if(assignmentTemp.getEndTime().isBefore(now)){
                                        continue;
                                    }
                                    UnfinishedAssignmentResponse.infoData infoData = new UnfinishedAssignmentResponse.infoData();
                                    infoData.setAssignmentId(assignmentTemp.getId());
                                    infoData.setTitle(assignmentTemp.getTitle());
                                    if(assignmentTemp.getDescription() != null){
                                        infoData.setDescription(assignmentTemp.getDescription());
                                    }
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    infoData.setStartTime(assignmentTemp.getStartTime().format(formatter));
                                    infoData.setEndTime(assignmentTemp.getEndTime().format(formatter));
                                    data.add(infoData);
                                }
                            }
                        }
                    }
                }
            }
            data.sort(Comparator.comparing(UnfinishedAssignmentResponse.infoData::getEndTime).reversed());
            response.setData(data);
            response.setMessage("未截止作业获取成功");
            operationLogger.info("学生{}获取未截止作业", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取未截止作业失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("{id}/get-finished-assignment-list")
    public ResponseEntity<FinishedAssignmentResponse> getFinishedAssignments(@PathVariable Long id) {
        try {
            FinishedAssignmentResponse response = new FinishedAssignmentResponse();
            List<FinishedAssignmentResponse.infoData> data = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            List<AssignmentRecipient> assignmentsByStudentId = assignmentRecipientService.selectByRecipient("STUDENT", id);
            if(assignmentsByStudentId != null){
                for(AssignmentRecipient assignment : assignmentsByStudentId){
                    Assignment assignmentTemp = assignmentService.selectById(assignment.getAssignmentId());
                    if(assignmentTemp.getEndTime().isAfter(now)){
                        continue;
                    }
                    FinishedAssignmentResponse.infoData infoData = new FinishedAssignmentResponse.infoData();
                    infoData.setAssignmentId(assignmentTemp.getId());
                    infoData.setTitle(assignmentTemp.getTitle());
                    if(assignmentTemp.getDescription() != null){
                        infoData.setDescription(assignmentTemp.getDescription());
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    infoData.setStartTime(assignmentTemp.getStartTime().format(formatter));
                    infoData.setEndTime(assignmentTemp.getEndTime().format(formatter));
                    AssignmentSubmission submission = assignmentSubmissionService.selectByAssignmentIdAndStudentId(assignment.getAssignmentId(), id);
                    if(submission != null && submission.getTotalScore() != null){
                        infoData.setTotalScore(submission.getTotalScore().doubleValue());
                    }
                    data.add(infoData);
                }
            }
            ClassStudent classStudent = classStudentService.getClassStudentByStudentId(id);
            if(classStudent != null){
                Long classId = classStudent.getClassId();
                if(classId != null){
                    List<AssignmentRecipient> assignmentsByClassId = assignmentRecipientService.selectByRecipient("CLASS", classId);
                    if(assignmentsByClassId != null){
                        for(AssignmentRecipient assignment : assignmentsByClassId){
                            Assignment assignmentTemp = assignmentService.selectById(assignment.getAssignmentId());
                            if(assignmentTemp.getEndTime().isAfter(now)){
                                continue;
                            }
                            FinishedAssignmentResponse.infoData infoData = new FinishedAssignmentResponse.infoData();
                            infoData.setAssignmentId(assignmentTemp.getId());
                            infoData.setTitle(assignmentTemp.getTitle());
                            if(assignmentTemp.getDescription() != null){
                                infoData.setDescription(assignmentTemp.getDescription());
                            }
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            infoData.setStartTime(assignmentTemp.getStartTime().format(formatter));
                            infoData.setEndTime(assignmentTemp.getEndTime().format(formatter));
                            AssignmentSubmission submission = assignmentSubmissionService.selectByAssignmentIdAndStudentId(assignment.getAssignmentId(), id);
                            if(submission != null && submission.getTotalScore() != null){
                                infoData.setTotalScore(submission.getTotalScore().doubleValue());
                            }
                            data.add(infoData);
                        }
                    }
                    List<GroupStudent> groupStudents = groupStudentService.getGroupStudentsByStudentId(id);
                    if(groupStudents != null){
                        for(GroupStudent groupStudent : groupStudents){
                            List<AssignmentRecipient> assignmentsByGroupId = assignmentRecipientService.selectByRecipient("GROUP", groupStudent.getGroupId());
                            if(assignmentsByGroupId != null){
                                for(AssignmentRecipient assignment : assignmentsByGroupId){
                                    Assignment assignmentTemp = assignmentService.selectById(assignment.getAssignmentId());
                                    if(assignmentTemp.getEndTime().isAfter(now)){
                                        continue;
                                    }
                                    FinishedAssignmentResponse.infoData infoData = new FinishedAssignmentResponse.infoData();
                                    infoData.setAssignmentId(assignmentTemp.getId());
                                    infoData.setTitle(assignmentTemp.getTitle());
                                    if(assignmentTemp.getDescription() != null){
                                        infoData.setDescription(assignmentTemp.getDescription());
                                    }
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    infoData.setStartTime(assignmentTemp.getStartTime().format(formatter));
                                    infoData.setEndTime(assignmentTemp.getEndTime().format(formatter));
                                    AssignmentSubmission submission = assignmentSubmissionService.selectByAssignmentIdAndStudentId(assignment.getAssignmentId(), id);
                                    if(submission != null && submission.getTotalScore() != null){
                                        infoData.setTotalScore(submission.getTotalScore().doubleValue());
                                    }
                                    data.add(infoData);
                                }
                            }
                        }
                    }
                }
            }
            data.sort(Comparator.comparing(FinishedAssignmentResponse.infoData::getEndTime).reversed());
            response.setData(data);
            response.setMessage("已截止作业获取成功");
            operationLogger.info("学生{}获取已截止作业", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取已截止作业失败，错误信息: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("{id}/homework/get-detail")
    public ResponseEntity<HomeworkDetailResponse> getHomeworkDetail(@PathVariable Long id, @RequestParam Long assignmentId) throws JsonProcessingException {
        try {
            HomeworkDetailResponse response = new HomeworkDetailResponse();
            List<HomeworkDetailResponse.infoData> data = new ArrayList<>();
            AssignmentSubmission oldSubmission = assignmentSubmissionService.selectByAssignmentIdAndStudentId(assignmentId, id);
            if(oldSubmission == null){
                Assignment assignment = assignmentService.selectById(assignmentId);
                if(assignment != null){
                    List<PaperQuestion> paperQuestions = paperQuestionService.selectByPaperId(assignment.getPaperId());
                    if(paperQuestions != null){
                        AssignmentSubmission submission = new AssignmentSubmission();
                        submission.setAssignmentId(assignmentId);
                        submission.setStudentId(id);
                        assignmentSubmissionService.insert(submission);
                        for(PaperQuestion paperQuestion : paperQuestions){
                            if(Objects.equals(paperQuestion.getQuestionType(), "small")){
                                Question question = questionService.getQuestionById(paperQuestion.getQuestionId());
                                if(question != null){
                                    SubmissionAnswer submissionAnswer = new SubmissionAnswer();
                                    submissionAnswer.setSubmissionId(submission.getId());
                                    submissionAnswer.setQuestionId(question.getId());
                                    submissionAnswer.setSequence(paperQuestion.getSequence().toString());
                                    submissionAnswer.setQuestionScore(Integer.parseInt(paperQuestion.getScore()));
                                    submissionAnswerService.insert(submissionAnswer);
                                    HomeworkDetailResponse.infoData infoData = new HomeworkDetailResponse.infoData();
                                    infoData.setSubmissionAnswerId(submissionAnswer.getId());
                                    infoData.setSequence(paperQuestion.getSequence().toString());
                                    if(question.getBodyId() != null){
                                        infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                                    }
                                    infoData.setQuestionContent(question.getContent());
                                    infoData.setQuestionType(question.getType());
                                    if("CHOICE".equals(question.getType())){
                                        infoData.setQuestionOptions(drawOptions(question.getOptions()));
                                    }
                                    data.add(infoData);
                                }
                            }
                            else if(Objects.equals(paperQuestion.getQuestionType(), "big")){
                                List<Question> questions = questionService.getQuestionsByQuestionBodyId(paperQuestion.getQuestionId());
                                if(questions != null && !questions.isEmpty()){
                                    int indexTemp = 1;
                                    String [] scores = paperQuestion.getScore().split("#");
                                    for(Question question : questions){
                                        SubmissionAnswer submissionAnswer = new SubmissionAnswer();
                                        submissionAnswer.setSubmissionId(submission.getId());
                                        submissionAnswer.setQuestionId(question.getId());
                                        submissionAnswer.setSequence(paperQuestion.getSequence() + "." + indexTemp);
                                        submissionAnswer.setQuestionScore(Integer.parseInt(scores[indexTemp - 1]));
                                        submissionAnswerService.insert(submissionAnswer);
                                        HomeworkDetailResponse.infoData infoData = new HomeworkDetailResponse.infoData();
                                        infoData.setSubmissionAnswerId(submissionAnswer.getId());
                                        infoData.setSequence(paperQuestion.getSequence() + "." + indexTemp);
                                        if(indexTemp == 1){
                                            infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                                        }
                                        infoData.setQuestionContent(question.getContent());
                                        infoData.setQuestionType(question.getType());
                                        if("CHOICE".equals(question.getType())){
                                            infoData.setQuestionOptions(drawOptions(question.getOptions()));
                                        }
                                        data.add(infoData);
                                        indexTemp++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
                List<SubmissionAnswer> submissionAnswers = submissionAnswerService.selectBySubmissionId(oldSubmission.getId());
                for(SubmissionAnswer submissionAnswer : submissionAnswers){
                    HomeworkDetailResponse.infoData infoData = new HomeworkDetailResponse.infoData();
                    infoData.setSubmissionAnswerId(submissionAnswer.getId());
                    infoData.setSequence(submissionAnswer.getSequence());
                    infoData.setAnswerContent(submissionAnswer.getAnswerContent());
                    Question question = questionService.getQuestionById(submissionAnswer.getQuestionId());
                    if(question != null){
                        infoData.setQuestionContent(question.getContent());
                        infoData.setQuestionType(question.getType());
                        if("CHOICE".equals(question.getType())){
                            infoData.setQuestionOptions(drawOptions(question.getOptions()));
                        }
                        String [] temp = infoData.getSequence().split("\\.");
                        if(temp.length > 1 && Objects.equals(temp[1], "1")){
                            infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                        }
                        else if(temp.length == 1 && question.getBodyId() != null){
                            infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                        }
                    }
                    data.add(infoData);
                }
            }
            response.setData(data);
            response.setMessage("success");
            Student student = studentService.getStudentById(id);
            operationLogger.info("学生{}获取了作业详情", student.info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取作业详情失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping("{id}/homework/complete")
    public ResponseEntity<Message> completeHomework(@PathVariable Long id, @RequestBody CompleteHomeworkRequest request) throws JsonProcessingException{
        try {
            Message response = new Message();
            for(CompleteHomeworkRequest.infoData infoData : request.getData()){
                SubmissionAnswer submissionAnswer = submissionAnswerService.selectById(infoData.getSubmissionAnswerId());
                submissionAnswer.setAnswerContent(infoData.getAnswerContent());
                submissionAnswerService.update(submissionAnswer);
            }
            CompleteHomeworkRequest.infoData infoData = request.getData().get(0);
            SubmissionAnswer submissionAnswer = submissionAnswerService.selectById(infoData.getSubmissionAnswerId());
            AssignmentSubmission submission = assignmentSubmissionService.selectById(submissionAnswer.getSubmissionId());
            submission.setSubmitTime(LocalDateTime.now());
            assignmentSubmissionService.update(submission);
            response.setMessage("success");
            operationLogger.info("学生{}提交了作业", studentService.getStudentById(id).info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生提交作业失败 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @GetMapping("{id}/homework/get-answer")
    public ResponseEntity<HomeworkAnswerResponse> getHomeworkAnswer(@PathVariable Long id, @RequestParam Long assignmentId) throws JsonProcessingException{
        try {
            HomeworkAnswerResponse response = new HomeworkAnswerResponse();
            List<HomeworkAnswerResponse.infoData> data = new ArrayList<>();
            AssignmentSubmission assignmentSubmission = assignmentSubmissionService.selectByAssignmentIdAndStudentId(assignmentId, id);
            if(assignmentSubmission != null){
                List<SubmissionAnswer> submissionAnswers = submissionAnswerService.selectBySubmissionId(assignmentSubmission.getId());
                for(SubmissionAnswer submissionAnswer : submissionAnswers){
                    HomeworkAnswerResponse.infoData infoData = new HomeworkAnswerResponse.infoData();
                    if(submissionAnswer.getScore() != null){
                        infoData.setScore(submissionAnswer.getScore());
                    }
                    infoData.setStudentAnswer(submissionAnswer.getAnswerContent());
                    infoData.setSequence(submissionAnswer.getSequence());
                    Question question = questionService.getQuestionById(submissionAnswer.getQuestionId());
                    if(question != null){
                        infoData.setQuestionContent(question.getContent());
                        infoData.setQuestionType(question.getType());
                        if("CHOICE".equals(question.getType())){
                            infoData.setQuestionOptions(drawOptions(question.getOptions()));
                        }
                        String [] temp = submissionAnswer.getSequence().split("\\.");
                        if(temp.length > 1 && Objects.equals(temp[1], "1")){
                            infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                        }
                        else if(temp.length == 1 && question.getBodyId() != null){
                            infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                        }
                        String [] temp2 = question.getAnswer().split("\\$\\$");
                        String answer = temp2[0].replace("##", ";");
                        infoData.setAnswer(answer);
                        if(temp2.length > 1){
                            infoData.setAnalysis(temp2[1]);
                        }
                    }
                    data.add(infoData);
                }
            }
            else{
                Assignment assignment = assignmentService.selectById(assignmentId);
                if(assignment != null){
                    List<PaperQuestion> paperQuestions = paperQuestionService.selectByPaperId(assignment.getPaperId());
                    if(paperQuestions != null){
                        AssignmentSubmission submission = new AssignmentSubmission();
                        submission.setAssignmentId(assignmentId);
                        submission.setStudentId(id);
                        assignmentSubmissionService.insert(submission);
                        for(PaperQuestion paperQuestion : paperQuestions){
                            if(Objects.equals(paperQuestion.getQuestionType(), "small")){
                                Question question = questionService.getQuestionById(paperQuestion.getQuestionId());
                                if(question != null){
                                    SubmissionAnswer submissionAnswer = new SubmissionAnswer();
                                    submissionAnswer.setQuestionId(question.getId());
                                    submissionAnswer.setSubmissionId(submission.getId());
                                    submissionAnswer.setSequence(paperQuestion.getSequence().toString());
                                    submissionAnswerService.insert(submissionAnswer);
                                    HomeworkAnswerResponse.infoData infoData = new HomeworkAnswerResponse.infoData();
                                    infoData.setSequence(paperQuestion.getSequence().toString());
                                    infoData.setQuestionContent(question.getContent());
                                    infoData.setQuestionType(question.getType());
                                    if("CHOICE".equals(question.getType())){
                                        infoData.setQuestionOptions(drawOptions(question.getOptions()));
                                    }
                                    String [] temp = submissionAnswer.getSequence().split("\\.");
                                    if(temp.length > 1 && Objects.equals(temp[1], "1")){
                                        infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                                    }
                                    else if(temp.length == 1 && question.getBodyId() != null){
                                        infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                                    }
                                    String [] temp2 = question.getAnswer().split("\\$\\$");
                                    String answer = temp2[0].replace("##", ";");
                                    infoData.setAnswer(answer);
                                    if(temp2.length > 1){
                                        infoData.setAnalysis(temp2[1]);
                                    }
                                    data.add(infoData);
                                }
                            }
                            else if(Objects.equals(paperQuestion.getQuestionType(), "big")){
                                List<Question> questions = questionService.getQuestionsByQuestionBodyId(paperQuestion.getQuestionId());
                                if(questions != null && !questions.isEmpty()){
                                    int indexTemp = 1;
                                    for(Question question : questions){
                                        SubmissionAnswer submissionAnswer = new SubmissionAnswer();
                                        submissionAnswer.setQuestionId(question.getId());
                                        submissionAnswer.setSubmissionId(submission.getId());
                                        submissionAnswer.setSequence(paperQuestion.getSequence() + "." + indexTemp);
                                        submissionAnswerService.insert(submissionAnswer);
                                        HomeworkAnswerResponse.infoData infoData = new HomeworkAnswerResponse.infoData();
                                        infoData.setSequence(paperQuestion.getSequence() + "." + indexTemp);
                                        if(indexTemp == 1){
                                            infoData.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                                        }
                                        infoData.setQuestionContent(question.getContent());
                                        infoData.setQuestionType(question.getType());
                                        if("CHOICE".equals(question.getType())){
                                            infoData.setQuestionOptions(drawOptions(question.getOptions()));
                                        }
                                        String [] temp2 = question.getAnswer().split("\\$\\$");
                                        String answer = temp2[0].replace("##", ";");
                                        infoData.setAnswer(answer);
                                        if(temp2.length > 1){
                                            infoData.setAnalysis(temp2[1]);
                                        }
                                        data.add(infoData);
                                        indexTemp++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            response.setData(data);
            response.setMessage("success");
            Student student = studentService.getStudentById(id);
            operationLogger.info("学生{}获取作业答案", student.info());
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("学生获取作业答案出现问题 {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

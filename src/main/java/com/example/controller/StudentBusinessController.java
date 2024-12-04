package com.example.controller;

import com.example.dto.redis.PreassembledPracticeQuestion;
import com.example.dto.redis.SubQuestion;
import com.example.dto.request.student.*;
import com.example.dto.response.Message;
import com.example.dto.response.student.*;
import com.example.model.course.KnowledgePoint;
import com.example.model.question.Practice;
import com.example.model.question.PracticeQuestion;
import com.example.model.question.Question;
import com.example.model.question.QuestionBody;
import com.example.model.submission.PracticeAnswer;
import com.example.service.course.KnowledgePointService;
import com.example.service.question.PracticeQuestionService;
import com.example.service.question.PracticeService;
import com.example.service.question.QuestionBodyService;
import com.example.service.question.QuestionService;
import com.example.service.submission.PracticeAnswerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/student")
public class StudentBusinessController {
    private final PracticeService practiceService;
    private final KnowledgePointService knowledgePointService;
    private final QuestionService questionService;
    private final PracticeQuestionService practiceQuestionService;
    private final PracticeAnswerService practiceAnswerService;
    private final QuestionBodyService questionBodyService;

    @Autowired
    public StudentBusinessController (PracticeService practiceService,
                                      KnowledgePointService knowledgePointService,
                                      QuestionService questionService,
                                      PracticeQuestionService practiceQuestionService,
                                      PracticeAnswerService practiceAnswerService,
                                      QuestionBodyService questionBodyService
                                      ) {
        this.practiceService = practiceService;
        this.knowledgePointService = knowledgePointService;
        this.questionService = questionService;
        this.practiceQuestionService = practiceQuestionService;
        this.practiceAnswerService = practiceAnswerService;
        this.questionBodyService = questionBodyService;
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
                    infoData.setQuestionBody(questionBody.getBody());
                    infoData.setQuestionContent(question.getContent());
                    infoData.setType(question.getType());
                    if (Objects.equals(infoData.getType(), "CHOICE")) {
                        infoData.setQuestionOptions(getOptionsByQuestion(question.getOptions()));
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
            List<PreassembledPracticeQuestion> preassembledQuestions = questionBodyService.getPreassembledQuestionsByTypes(types);
            List<PreassembledPracticeQuestion> finalBigQuestions = new ArrayList<>();
            for (GeneratePracticeDefineRequest.QuestionBodyType questionBodyType : request.getQuestionBodyTypes()) {
                //取出当前类型
                List<PreassembledPracticeQuestion> typeQuestions = preassembledQuestions.stream()
                        .filter(q -> q.getType().equals(questionBodyType.getType()))
                        .collect(Collectors.toList());

                if (typeQuestions.isEmpty()) continue;

                // 高效随机抽取
                Collections.shuffle(typeQuestions);
                List<PreassembledPracticeQuestion> selectedTypeQuestions = typeQuestions.stream()
                        .limit(questionBodyType.getNum())
                        .toList();
                finalBigQuestions.addAll(selectedTypeQuestions);
            }
            for (PreassembledPracticeQuestion preassembledQuestion : finalBigQuestions) {
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
                        infoData.setQuestionOptions(getOptionsByQuestion(subQ.getQuestionOptions()));
                    }
                    data.add(infoData);
                }

            }
        }

        response.setPracticeId(practice.getId());
        response.setData(data);
        response.setMessage("练习生成成功");
        return ResponseEntity.ok(response);
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




//    @PostMapping("/{id}/practice/generate-auto")//暂时
//    public ResponseEntity<GeneratePracticeDefineResponse> generatePracticeAuto(@PathVariable Long id, @RequestBody GeneratePracticeAutoRequest request) {
//        GeneratePracticeDefineResponse response = new GeneratePracticeDefineResponse();
//        List<Question> questions = questionService.getAllQuestions();
//        // 创建练习
//        Practice practice = new Practice();
//        practice.setStudentId(id);
//        practice.setName(request.getName());
//        practiceService.createPractice(practice);
//        response.setPracticeId(practice.getId());
//
//        // 初始化练习题目列表
//        List<PracticeQuestion> practiceQuestions = new ArrayList<>();
//        List<GeneratePracticeDefineResponse.InfoData> data = new ArrayList<>();
//
//        // 随机选择题目并存储
//        for (int i = 0; i < 10; i++) {
//            int index = (int) (Math.random() * questions.size());
//            Question question = questions.get(index);
//
//            PracticeQuestion practiceQuestion = new PracticeQuestion();
//            practiceQuestion.setPracticeId(practice.getId());
//            practiceQuestion.setQuestionId(question.getId());
//
//            // 设置初始的类型优先级
//            if(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody() != null && (! questionBodyService.getQuestionBodyById(question.getBodyId()).getBody().isEmpty())){
//                practiceQuestion.setSequence("8");
//            }
//            else if (Objects.equals(question.getType(), "CHOICE")) {
//                practiceQuestion.setSequence("1");
//            }
//            else if (Objects.equals(question.getType(), "FILL_IN_BLANK")) {
//                practiceQuestion.setSequence("2");
//            }
//            else if (Objects.equals(question.getType(), "SHORT_ANSWER")) {
//                practiceQuestion.setSequence("3");
//            }
//            else if (Objects.equals(question.getType(), "ESSAY")) {
//                practiceQuestion.setSequence("9");
//            }
//            practiceQuestions.add(practiceQuestion);
//        }
//
//        // 按题目类型排序
//        practiceQuestions.sort(Comparator.comparingInt(pq -> Integer.parseInt(pq.getSequence())));
//
//        // 设置最终的序列值并保存
//        int finalSequence = 1;
//        for (PracticeQuestion practiceQuestion : practiceQuestions) {
//            QuestionBody questionBody = questionBodyService.getQuestionBodyById(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId());
//            if(questionBody.getBody() != null && (! questionBody.getBody().isEmpty())){
//                int i = 1;
//                List<Question> questionsByBody = questionService.getQuestionsByQuestionBodyId(questionBody.getId());
//                for (Question question : questionsByBody) {
//                    PracticeQuestion littlePracticeQuestion = new PracticeQuestion();
//                    littlePracticeQuestion.setPracticeId(practice.getId());
//                    littlePracticeQuestion.setQuestionId(question.getId());
//                    littlePracticeQuestion.setSequence(finalSequence + "." + i);
//                    practiceQuestionService.addPracticeQuestion(littlePracticeQuestion);
//                    GeneratePracticeDefineResponse.InfoData infoData = new GeneratePracticeDefineResponse.InfoData();
//                    infoData.setPracticeQuestionId(littlePracticeQuestion.getId());
//                    infoData.setQuestionBody(null);
//                    infoData.setQuestionContent(question.getContent().replace("<p>", "").replace("</p>", ""));
//                    infoData.setType(question.getType());
//                    infoData.setQuestionOptions(new ArrayList<>());
//                    infoData.setSequence(littlePracticeQuestion.getSequence());
//                    if(Objects.equals(question.getType(), "CHOICE")){
//                        List<String> choices = getStrings(question);
//                        infoData.getQuestionOptions().addAll(choices);
//                    }
//                    if(i == 1){
//                        infoData.setQuestionBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody().replace("<p>", "").replace("</p>", ""));
//                    }
//                    data.add(infoData);
//                    i++;
//                }
//                finalSequence++;
//            }
//            else{
//                Question question = questionService.getQuestionById(practiceQuestion.getQuestionId());
//                practiceQuestion.setSequence(String.valueOf(finalSequence));
//                practiceQuestionService.addPracticeQuestion(practiceQuestion);
//                GeneratePracticeDefineResponse.InfoData infoData = new GeneratePracticeDefineResponse.InfoData();
//                infoData.setPracticeQuestionId(practiceQuestion.getId());
//                infoData.setQuestionBody(null);
//                infoData.setQuestionContent(question.getContent().replace("<p>", "").replace("</p>", ""));
//                infoData.setType(question.getType());
//                infoData.setQuestionOptions(new ArrayList<>());
//                infoData.setSequence(finalSequence + "");
//                if(Objects.equals(question.getType(), "CHOICE")){
//                    List<String> choices = getStrings(question);
//                    infoData.getQuestionOptions().addAll(choices);
//                }
//                data.add(infoData);
//                finalSequence++;
//            }
//        }
//
//        // 返回响应
//        response.setData(data);
//        response.setMessage("练习生成成功");
//        return ResponseEntity.ok(response);
//    }

    private static List<String> getOptionsByQuestion(String optionString) {
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
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{id}/continue-practice")
    public ResponseEntity<ContinuePracticeResponse> continuePractice(@PathVariable Long id, @RequestBody ContinuePracticeRequest request) throws JsonProcessingException {
        ContinuePracticeResponse response = new ContinuePracticeResponse();
        List<PracticeQuestion> practiceQuestions = practiceQuestionService.getPracticeQuestionByPracticeId(request.getPracticeId());
        List<ContinuePracticeResponse.InfoData> data = new ArrayList<>();
        for(PracticeQuestion practiceQuestion : practiceQuestions){
            ContinuePracticeResponse.InfoData infoData = new ContinuePracticeResponse.InfoData();
            infoData.setPracticeQuestionId(practiceQuestion.getId());
            infoData.setSequence(practiceQuestion.getSequence());
            infoData.setQuestionContent(questionService.getQuestionById(practiceQuestion.getQuestionId()).getContent().replace("<p>", "").replace("</p>", ""));
            infoData.setQuestionType(questionService.getQuestionById(practiceQuestion.getQuestionId()).getType());
            infoData.setQuestionOptions(new ArrayList<>());
            if(Objects.equals(infoData.getQuestionType(), "CHOICE")){
                Question question = questionService.getQuestionById(practiceQuestion.getQuestionId());
                List<String> choices = getOptionsByQuestion(question.getOptions());
                infoData.getQuestionOptions().addAll(choices);
            }
            infoData.setAnswerContent(practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId()).getAnswerContent());
            String [] sequences = infoData.getSequence().split("\\.");
            infoData.setQuestionBody("");
            if(sequences.length > 1 && sequences[1].equals("1")){
                infoData.setQuestionBody(questionBodyService.getQuestionBodyById(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId()).getBody().replace("<p>", "").replace("</p>", ""));
            }
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("获取成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/practice/complete")
    public ResponseEntity<CompletePracticeResponse> completePractice(@PathVariable Long id, @RequestBody CompletePracticeRequest request) throws JsonProcessingException {
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
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/practice/get-answer")
    public ResponseEntity<GetAnswerResponse> getAnswer(@PathVariable Long id, @RequestParam Long practiceId) throws JsonProcessingException {
        GetAnswerResponse response = new GetAnswerResponse();
        response.setTotalScore(practiceService.getPracticeById(practiceId).getTotalScore().doubleValue());
        List<PracticeQuestion> practiceQuestions = practiceQuestionService.getPracticeQuestionByPracticeId(practiceId);
        List<GetAnswerResponse.InfoData> data = new ArrayList<>();
        for(PracticeQuestion practiceQuestion : practiceQuestions){
            GetAnswerResponse.InfoData infoData = new GetAnswerResponse.InfoData();
            infoData.setQuestionContent(questionService.getQuestionById(practiceQuestion.getQuestionId()).getContent().replace("<p>", "").replace("</p>", ""));
            infoData.setQuestionType(questionService.getQuestionById(practiceQuestion.getQuestionId()).getType());
            infoData.setSequence(practiceQuestion.getSequence());
            infoData.setScore(null);
            infoData.setQuestionOptions(new ArrayList<>());
            if(Objects.equals(infoData.getQuestionType(), "CHOICE")){
                Question question = questionService.getQuestionById(practiceQuestion.getQuestionId());
                List<String> answerArray = getOptionsByQuestion(question.getOptions());
                infoData.setQuestionOptions(answerArray);
                if(practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId()).getScore() != null){
                    infoData.setScore(practiceAnswerService.getPracticeAnswerByPracticeQuestionId(practiceQuestion.getId()).getScore().doubleValue());
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
            if(sequenceArray.length > 1 && Objects.equals(sequenceArray[1], "1")){
                infoData.setQuestionBody(questionBodyService.getQuestionBodyById(questionService.getQuestionById(practiceQuestion.getQuestionId()).getBodyId()).getBody().replace("<p>", "").replace("</p>", ""));
            }
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("答案获取成功");
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}/delete-practice")
    public ResponseEntity<Message> deletePractice(@PathVariable Long id, @RequestParam Long practiceId) {
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
        return ResponseEntity.ok(response);
    }

}

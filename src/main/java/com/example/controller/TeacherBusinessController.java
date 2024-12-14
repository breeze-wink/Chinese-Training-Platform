package com.example.controller;

import com.example.dto.mapper.QuestionStatisticDTO;
import com.example.dto.request.teacher.*;
import com.example.dto.response.*;
import com.example.dto.response.student.AvgScoreResponse;
import com.example.dto.response.student.HistoryScoresResponse;
import com.example.dto.response.student.MultidimensionalScoresResponse;
import com.example.dto.response.student.WeaknessScoresResponse;
import com.example.dto.response.teacher.*;
import com.example.model.classes.*;
import com.example.model.course.CourseStandard;
import com.example.model.course.KnowledgePoint;
import com.example.model.question.*;
import com.example.model.user.BaseUser;
import com.example.service.cache.CacheRefreshService;
import com.example.model.submission.AssignmentSubmission;
import com.example.model.user.StatsStudent;
import com.example.service.classes.*;
import com.example.service.classes.impl.*;
import com.example.service.course.CourseStandardService;
import com.example.service.course.KnowledgePointService;
import com.example.service.course.impl.CourseStandardServiceImpl;
import com.example.service.course.impl.KnowledgePointServiceImpl;
import com.example.service.question.*;
import com.example.service.question.impl.AssignmentServiceImpl;
import com.example.service.question.impl.QuestionBodyServiceImpl;
import com.example.service.question.impl.QuestionServiceImpl;
import com.example.service.question.impl.UploadQuestionServiceImpl;
import com.example.service.submission.AssignmentSubmissionService;
import com.example.service.submission.impl.AssignmentSubmissionServiceImpl;
import com.example.service.user.StatsStudentService;
import com.example.service.user.StudentService;
import com.example.service.user.TeacherService;
import com.example.service.user.impl.StatsStudentServiceImpl;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.user.impl.TeacherServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teacher")
public class TeacherBusinessController {
    private final CourseStandardService courseStandardService;
    private final ClassService classService;
    private final ClassGroupService classGroupService;
    private final ClassStudentService classStudentService;
    private final StudentService studentService;
    private final GroupStudentService groupStudentService;
    private final KnowledgePointService knowledgePointService;
    private final CacheRefreshService cacheRefreshService;
    private final QuestionService questionService;
    private final QuestionBodyService questionBodyService;
    private final JoinClassService joinClassService;
    private final StatsStudentService statsStudentService;
    private final SearchQuestionService searchQuestionService;
    private final QuestionStatisticService questionStatisticService;
    private final AssignmentSubmissionService assignmentSubmissionService;
    private final UploadQuestionService uploadQuestionService;
    private final TestPaperService testPaperService;
    private final PaperQuestionService paperQuestionService;
    private final TeacherService teacherService;
    private final AssignmentService assignmentService;
    @Autowired
    public TeacherBusinessController(CourseStandardServiceImpl courseStandardService,
                                     ClassServiceImpl classService,
                                     ClassGroupServiceImpl classGroupService,
                                     ClassStudentServiceImpl classStudentService,
                                     StudentServiceImpl studentService,
                                     GroupStudentServiceImpl groupStudentService,
                                     KnowledgePointServiceImpl knowledgePointService,
                                     QuestionServiceImpl questionService,
                                     QuestionBodyServiceImpl questionBodyService,
                                     JoinClassServiceImpl joinClassService,
                                     StatsStudentServiceImpl statsStudentService,
                                     AssignmentSubmissionServiceImpl assignmentSubmissionService,
                                     CacheRefreshService cacheRefreshService,
                                     SearchQuestionService searchQuestionService,
                                     QuestionStatisticService questionStatisticService,
                                     TeacherServiceImpl teacherService,
                                     TestPaperService testPaperService,
                                     PaperQuestionService paperQuestionService,
                                     AssignmentServiceImpl assignmentService,
                                     UploadQuestionServiceImpl uploadQuestionService
                                     ) {
        this.courseStandardService = courseStandardService;
        this.classService = classService;
        this.classGroupService = classGroupService;
        this.classStudentService = classStudentService;
        this.studentService = studentService;
        this.groupStudentService = groupStudentService;
        this.knowledgePointService = knowledgePointService;
        this.questionService = questionService;
        this.questionBodyService = questionBodyService;
        this.joinClassService = joinClassService;
        this.cacheRefreshService = cacheRefreshService;
        this.statsStudentService = statsStudentService;
        this.assignmentSubmissionService = assignmentSubmissionService;
        this.searchQuestionService = searchQuestionService;
        this.questionStatisticService = questionStatisticService;
        this.teacherService = teacherService;
        this.testPaperService = testPaperService;
        this.paperQuestionService = paperQuestionService;
        this.assignmentService = assignmentService;
        this.uploadQuestionService = uploadQuestionService;
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
        response.setClassId(clazz.getId());
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
    public ResponseEntity<CreateGroupResponse> createGroup(@RequestBody CreateGroupRequest request ) {
        CreateGroupResponse response = new CreateGroupResponse();

        try {
            ClassGroup group = classGroupService.createGroup(request.getClassId(), request.getGroupName(), request.getGroupDescription());
            Long groupId = group.getId();
            for (Long studentId : request.getStudentIds()) {
                classGroupService.addStudentToGroup(groupId, studentId);
            }

            response.setGroupId(groupId);
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

    @DeleteMapping("/{teacherId}/classes/disband")

    public ResponseEntity<Message> disbandClass(@PathVariable Long teacherId, @RequestParam Long classId) {
        Message response = new Message();
        if (classService.removeClass(classId) == 1) {
            response.setMessage("班级解散成功");
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("班级解散失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{teacherId}/disband-group")
    public ResponseEntity<Message> disbandGroup(@PathVariable Long teacherId, @RequestParam Long groupId) {
        Message response = new Message();

        try {
            ClassGroup group = classGroupService.getGroupById(groupId);
            if (group == null) {
                response.setMessage("小组解散失败, 找不到该小组");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            classGroupService.removeGroup(groupId);
            response.setMessage("成功解散小组");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("小组解散失败" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/{teacherId}/groups-members")
    public ResponseEntity<GroupMembersResponse> getGroupMembers(@PathVariable Long teacherId,
                                                                @RequestParam Long groupId) {
        GroupMembersResponse response = new GroupMembersResponse();
        response.setStudents(new ArrayList<>());
        try {
            List<GroupStudent> groupStudents = groupStudentService.getGroupStudentsByGroupId(groupId);

            for (GroupStudent groupStudent : groupStudents) {
                GroupMembersResponse.memberInfo info = new GroupMembersResponse.memberInfo();
                info.setStudentId(groupStudent.getStudentId());
                info.setStudentName(studentService.getStudentById(groupStudent.getStudentId()).getName());
                response.getStudents().add(info);
            }

            response.setMessage("小组成员构成获取成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("获取失败:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
    @PutMapping("/{id}/update-class")
    public ResponseEntity<Message> updateClass(@PathVariable Long id, @RequestBody UpdateClassRequest request) {
        Long classId = request.getClassId();
        Clazz clazz = classService.getClassById(classId);
        if (clazz == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("更新失败，未找到班级"));
        }
        clazz.setName(request.getClassName());
        clazz.setDescription(request.getClassDescription());
        classService.updateClass(clazz);

        return ResponseEntity.ok(new Message("更新成功"));
    }
    @GetMapping("/{id}/view-knowledge-point")
    public ResponseEntity<KnowledgePointsResponse> viewKnowledgePoints(@PathVariable Long id) {
        KnowledgePointsResponse response = new KnowledgePointsResponse();

        try {
            response.setKnowledgePoints(knowledgePointService.getAllKnowledgePointsWithDescriptionGroupByType());
            response.setMessage("获取成功");

            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            e.printStackTrace();
            response.setMessage("获取失败" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}/list-knowledge-point")
    public ResponseEntity<ListKnowledgeResponse> getKnowledgePoint(@PathVariable Long id) {
        ListKnowledgeResponse response = new ListKnowledgeResponse();
        try {
            response.setKnowledgePoints(knowledgePointService.getAllKnowledgePointsGroupByType());

            response.setMessage("获取成功");

            return ResponseEntity.ok(response);
        }  catch (Exception e) {
            e.printStackTrace();
            response.setMessage("获取失败" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/{id}/upload-question")
    public ResponseEntity<Message>  uploadQuestion(@PathVariable Long id, @RequestBody UploadQuestionRequest request) {
        Message response = new Message();
        try {
            QuestionBody questionBody = new QuestionBody();
           
            questionBody.setBody(request.getBody());
            questionBody.setType(request.getQuestionType());

            List<UploadQuestionRequest.QuestionInfo> questions = request.getQuestions();

            if (questionBody.getBody() != null && !questionBody.getBody().isEmpty()) {
                questionBodyService.createQuestionBody(questionBody);
                QuestionStatistic questionStatistic = new QuestionStatistic();
                questionStatistic.setId(questionBody.getId());
                questionStatistic.setType("big");
                questionStatisticService.insert(questionStatistic);
                // 上传信息
                UploadQuestion uploadQuestion = new UploadQuestion();
                uploadQuestion.setTeacherId(id);
                uploadQuestion.setQuestionId(questionBody.getId());
                uploadQuestion.setType("big");
                uploadQuestionService.create(uploadQuestion);
            }

            for (UploadQuestionRequest.QuestionInfo questionInfo : questions) {
                Question question = getQuestion(questionInfo, questionBody);
                questionService.createQuestion(question);

                //上传信息
                if (questions.size() == 1) {
                    UploadQuestion uploadQuestion = new UploadQuestion();
                    uploadQuestion.setTeacherId(id);
                    uploadQuestion.setQuestionId(question.getId());
                    uploadQuestion.setType("small");
                    uploadQuestionService.create(uploadQuestion);
                }

                QuestionStatistic questionStatistic = new QuestionStatistic();
                questionStatistic.setId(question.getId());
                questionStatistic.setType("small");
                questionStatisticService.insert(questionStatistic);
                cacheRefreshService.markKnowledgeCacheOutOfDate(question.getKnowledgePointId());
            }
            if (questionBody.getBody() != null && !questionBody.getBody().isEmpty()) {
                cacheRefreshService.markTypeCacheOutOfDate(request.getQuestionType());
            }
            return ResponseEntity.ok(new Message("上传成功"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("上传失败" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private static Question getQuestion(UploadQuestionRequest.QuestionInfo questionInfo, QuestionBody questionBody) {
        Question question = new Question();
        question.setBodyId(questionBody.getBody() == null ? null : questionBody.getId());
        question.setType(questionInfo.getType());
        question.setContent(questionInfo.getProblem());
        question.setKnowledgePointId(questionInfo.getKnowledgePointId());

        StringBuilder resAnswer = new StringBuilder();
        if(questionInfo.getAnswer() != null && (!questionInfo.getAnswer().isEmpty())){
            for (int i = 0; i < questionInfo.getAnswer().size(); i ++) {
                if(questionInfo.getAnswer().get(i) == null || questionInfo.getAnswer().get(i).isEmpty()){
                    resAnswer.append("略");
                }
                else{
                    resAnswer.append(questionInfo.getAnswer().get(i));
                }
                if (i != questionInfo.getAnswer().size() - 1) {
                    resAnswer.append("##");
                }
            }

        }
        else{
            resAnswer.append(" ");
        }
        question.setAnswer(resAnswer + "$$" + questionInfo.getAnalysis());

        if (questionInfo.getType().equals("CHOICE")) {
            StringBuilder choices = new StringBuilder();

            for (int i = 0; i < questionInfo.getChoices().size(); i ++) {
                choices.append(questionInfo.getChoices().get(i));
                if(questionInfo.getChoices().get(i) == null || questionInfo.getChoices().get(i).isEmpty()){
                    choices.append(" ");
                }
                if (i != questionInfo.getChoices().size() - 1) {
                    choices.append("$$");
                }
            }
            question.setOptions(choices.toString());
        }
        return question;
    }


    @GetMapping("/{id}/get-applications")
    public ResponseEntity<GetApplicationsResponse> getApplications(@PathVariable Long id, @RequestParam Long classId) {
        GetApplicationsResponse response = new GetApplicationsResponse();
        if(classService.getClassById(classId) == null){
            response.setMessage("班级不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(!Objects.equals(classService.getClassById(classId).getCreatorId(), id)){
            response.setMessage("无权限");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<GetApplicationsResponse.infoData> data = new ArrayList<>();
        List<JoinClass> joinClasses = joinClassService.selectJoinClassByClassId(classId);
        for (JoinClass joinClass : joinClasses) {
            GetApplicationsResponse.infoData infoData = new GetApplicationsResponse.infoData();
            infoData.setJoinClassId(joinClass.getId());
            infoData.setStudentId(joinClass.getStudentId());
            infoData.setUserName(studentService.getStudentById(joinClass.getStudentId()).getUsername());
            infoData.setName(studentService.getStudentById(joinClass.getStudentId()).getName());
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("获取申请列表成功");
        return ResponseEntity.ok(response);
    }



    @GetMapping("/{id}/allow-application")
    public ResponseEntity<Message> allowApplication(@PathVariable Long id, @RequestParam Long joinClassId) {
        Message response = new Message();
        JoinClass joinClass = joinClassService.selectJoinClassById(joinClassId);
        if(joinClass == null){
            response.setMessage("申请不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else if(!Objects.equals(classService.getClassById(joinClass.getClassId()).getCreatorId(), id)){
            response.setMessage("无权限");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else{
            ClassStudent classStudent = new ClassStudent();
            classStudent.setClassId(joinClass.getClassId());
            classStudent.setStudentId(joinClass.getStudentId());
            classStudent.setJoinDate(new Date());
            classStudentService.addClassStudent(classStudent);
            joinClassService.removeJoinClassByStudentId(joinClass.getStudentId());
        }
        response.setMessage("操作成功");
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{teacherId}/classes/remove-student")
    public ResponseEntity<Message> removeStudentFromClass(@PathVariable Long teacherId, @RequestParam Long studentId) {
        Message response = new Message();
        List<ClassStudent> classStudents = classStudentService.getClassStudentsByStudentId(studentId);
        if(classStudents.isEmpty()){
            response.setMessage("学生不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        ClassStudent classStudent = classStudents.get(0);
        if(!Objects.equals(classService.getClassById(classStudent.getClassId()).getCreatorId(), teacherId)){
            response.setMessage("无权限");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        classService.removeStudentFromClass(classStudent.getClassId(), studentId);
        response.setMessage("删除学生成功");
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}/group/remove-student")
    public ResponseEntity<Message> removeStudentFromGroup(@PathVariable Long id,
                                                          @RequestParam Long groupId,
                                                          @RequestParam Long studentId) {
        Message response = new Message();
        GroupStudent groupStudent = groupStudentService.getGroupStudentByIds(groupId, studentId);
        if(groupStudent == null){
            response.setMessage("小组或学生不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(!Objects.equals(classService.getClassById(classGroupService.getGroupById(groupId).getClassId()).getCreatorId(), id)){
            response.setMessage("无权限");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(groupStudentService.removeGroupStudent(groupId, studentId) == 1){
            response.setMessage("删除学生成功");
            return ResponseEntity.ok(response);
        }
        else{
            response.setMessage("未知错误");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



    @GetMapping("{id}/get-all-questions")
    public ResponseEntity<GetAllQuestionsResponse> getAllQuestions(@PathVariable Long id) {
        //TODO:
        GetAllQuestionsResponse response = new GetAllQuestionsResponse();
        response.setMessage("获取成功");
        return ResponseEntity.ok(response);
    }



    @GetMapping("{id}/get-question")
    public ResponseEntity<GetQuestionResponse> getQuestion(@PathVariable Long id, @RequestParam Long questionId) throws JsonProcessingException {
        GetQuestionResponse response = new GetQuestionResponse();
        List<GetQuestionResponse.infoData> data = new ArrayList<>();
        Question question = questionService.getQuestionById(questionId);
        if(question == null){
            response.setMessage("问题不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setCreator(null);
        response.setKnowledgePointType(knowledgePointService.getKnowledgePointById(question.getKnowledgePointId()).getType());
        response.setBody(null);
        response.setBodyId(null);
        List<Question> questions = new ArrayList<>();
        if(question.getBodyId() != null){
            response.setBodyId(question.getBodyId());
            questions = questionService.getQuestionsByQuestionBodyId(question.getBodyId());
            response.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
            Long creatorId = uploadQuestionService.findTeacherIdByQuestionIdAndType(question.getId(), "small");
            response.setCreator(teacherService.getTeacherById(creatorId).getName());
        }
        else{
            questions.add(question);
            Long creatorId = uploadQuestionService.findTeacherIdByQuestionIdAndType(question.getId(), "big");
            response.setCreator(teacherService.getTeacherById(creatorId).getName());
        }
        for(Question q : questions){
            GetQuestionResponse.infoData infoData = new GetQuestionResponse.infoData();
            infoData.setQuestionId(q.getId());
            infoData.setContent(q.getContent());
            infoData.setType(q.getType());
            if(Objects.equals(q.getType(), "CHOICE") && q.getOptions() != null){
                infoData.setOptions(drawOptions(q.getOptions()));
            }
            String [] temp = q.getAnswer().split("\\$\\$");
            infoData.setAnswer(temp[0]);
            infoData.setAnalysis(null);
            if(temp.length > 1){
                infoData.setAnalysis(temp[1]);
            }
            infoData.setKnowledgePointName(knowledgePointService.getKnowledgePointById(q.getKnowledgePointId()).getName());
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("获取问题成功");
        return ResponseEntity.ok(response);
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


    @DeleteMapping("{id}/delete-question")
    public ResponseEntity<Message> deleteQuestion(@PathVariable Long id, @RequestParam Long deleteId, @RequestParam String type) throws JsonProcessingException {
        Message response = new Message();
        if(Objects.equals(type, "small")){
            Question question = questionService.getQuestionById(deleteId);
            if(question == null){
                response.setMessage("问题不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            if(question.getBodyId() == null || questionService.getQuestionsByQuestionBodyId(question.getBodyId()).size() > 1){
                questionService.deleteQuestion(deleteId);
            }
            else{
                questionBodyService.deleteQuestionBody(question.getBodyId());

            }
        }
        else if(Objects.equals(type, "big")){
            QuestionBody questionBody = questionBodyService.getQuestionBodyById(deleteId);
            if(questionBody == null){
                response.setMessage("问题不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            questionBodyService.deleteQuestionBody(deleteId);
        }
        response.setMessage("删除成功");
        return ResponseEntity.ok(response);
    }



    @GetMapping("{id}/get-student-situation")
    public ResponseEntity<AvgScoreResponse> getAvgScore(@PathVariable Long id, @RequestParam Long studentId) {
        AvgScoreResponse response = new AvgScoreResponse();
        AvgScoreResponse.infoData data = new AvgScoreResponse.infoData();
        data.setAverageHomeworkScore(null);
        data.setClassRank(null);
        Clazz clazz = classService.getClassById(classStudentService.getClassStudentByStudentId(studentId).getClassId());
        if(!Objects.equals(clazz.getCreatorId(), id)){
            response.setMessage("无权限");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<StatsStudent> statsStudents = statsStudentService.getStatsStudentByStudentId(studentId);
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
        ClassStudent classStudent = classStudentService.getClassStudentByStudentId(studentId);
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
                if(stats[i][0] == studentId){
                    data.setClassRank((long)(stats.length - i));
                    break;
                }
            }
        }
        response.setData(data);
        response.setMessage("平均分获取成功");
        return ResponseEntity.ok(response);
    }


    @GetMapping("{id}/get-student-multidimensional-scores")
    public ResponseEntity<MultidimensionalScoresResponse> getMultidimensionalScores(@PathVariable Long id, @RequestParam Long studentId) {
        MultidimensionalScoresResponse response = new MultidimensionalScoresResponse();
        Clazz clazz = classService.getClassById(classStudentService.getClassStudentByStudentId(studentId).getClassId());
        if(!Objects.equals(clazz.getCreatorId(), id)){
            response.setMessage("无权限");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<MultidimensionalScoresResponse.infoData> data = new ArrayList<>();
        List<KnowledgePoint> knowledgePoints = knowledgePointService.getAllKnowledgePoints();
        List<StatsStudent> statsStudents = statsStudentService.getStatsStudentByStudentId(studentId);
        for(KnowledgePoint knowledgePoint : knowledgePoints) {
            MultidimensionalScoresResponse.infoData infoData = new MultidimensionalScoresResponse.infoData();
            infoData.setName(knowledgePoint.getType());
            infoData.setScore(null);
            boolean flag = false;
            for (MultidimensionalScoresResponse.infoData datum : data) {
                if (datum.getName().equals(knowledgePoint.getType())) {
                    flag = true;
                    break;
                }
            }
            if(!flag){
                long totalScore = 0L;
                long score = 0L;
                for(StatsStudent statsStudent : statsStudents) {
                    if(knowledgePointService.getKnowledgePointById(statsStudent.getKnowledgePointId()).getType().equals(infoData.getName())){
                        if(statsStudent.getTotalScore() != null){
                            totalScore += statsStudent.getTotalScore();
                        }
                        if(statsStudent.getScore() != null){
                            score += statsStudent.getScore();
                        }
                    }
                }
                if(totalScore != 0){
                    double scorePercentage = 100 * (double) score / (double) totalScore;
                    scorePercentage = Double.parseDouble(String.format("%.2f", scorePercentage));
                    infoData.setScore(scorePercentage);
                }
                data.add(infoData);
            }
        }
        response.setData(data);
        response.setMessage("各项成绩获取成功");
        return ResponseEntity.ok(response);
    }



    @GetMapping("{id}/get-student-weakness-scores")
    public ResponseEntity<WeaknessScoresResponse> getWeaknessScores(@PathVariable Long id, @RequestParam Long studentId) {
        WeaknessScoresResponse response = new WeaknessScoresResponse();
        Clazz clazz = classService.getClassById(classStudentService.getClassStudentByStudentId(studentId).getClassId());
        if(!Objects.equals(clazz.getCreatorId(), id)){
            response.setMessage("无权限");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<WeaknessScoresResponse.infoData> data = new ArrayList<>();
        List<KnowledgePoint> knowledgePoints = knowledgePointService.getAllKnowledgePoints();
        List<StatsStudent> statsStudents = statsStudentService.getStatsStudentByStudentId(studentId);
        for(KnowledgePoint knowledgePoint : knowledgePoints) {
            WeaknessScoresResponse.infoData infoData = new WeaknessScoresResponse.infoData();
            infoData.setType(knowledgePoint.getType());
            infoData.setWeaknessName(null);
            infoData.setWeaknessScore(null);
            boolean flag = false;
            for (WeaknessScoresResponse.infoData datum : data) {
                if (datum.getType().equals(knowledgePoint.getType())) {
                    flag = true;
                    break;
                }
            }
            if(!flag){
                for(StatsStudent statsStudent : statsStudents) {
                    KnowledgePoint knowledgePointTemp = knowledgePointService.getKnowledgePointById(statsStudent.getKnowledgePointId());
                    if(knowledgePointTemp.getType().equals(infoData.getType())){
                        double scoreTemp;
                        if(statsStudent.getTotalScore() != null && statsStudent.getScore() != null){
                            scoreTemp = 100 * (double) statsStudent.getScore() / (double) statsStudent.getTotalScore();
                        }
                        else{
                            scoreTemp = 0;
                        }
                        if(infoData.getWeaknessName() == null || infoData.getWeaknessScore() == null || scoreTemp < infoData.getWeaknessScore()){
                            infoData.setWeaknessName(knowledgePointTemp.getName());
                            infoData.setWeaknessScore(scoreTemp);
                        }
                    }
                }
                data.add(infoData);
            }
        }
        response.setData(data);
        response.setMessage("短板获取成功");
        return ResponseEntity.ok(response);
    }



    @GetMapping("{id}/get-student-historical-homework-scores")
    public ResponseEntity<HistoryScoresResponse> getHistoryScores(@PathVariable Long id, @RequestParam Long studentId) {
        HistoryScoresResponse response = new HistoryScoresResponse();
        List<HistoryScoresResponse.infoData> data = new ArrayList<>();
        List<AssignmentSubmission> submissions = assignmentSubmissionService.selectByStudentId(studentId);
        for(int i = 0; i < submissions.size() && i < 10; i++){
            HistoryScoresResponse.infoData infoData = new HistoryScoresResponse.infoData();
            Assignment assignment = assignmentService.selectById(submissions.get(i).getAssignmentId());
            infoData.setDate(assignment.getEndTime().toString());
            if(submissions.get(i).getTotalScore() != null){
                infoData.setScore(Double.valueOf(String.valueOf(submissions.get(i).getTotalScore())));
            }
            data.add(infoData);
        }
        data.sort(Comparator.comparing(HistoryScoresResponse.infoData::getDate).reversed());
        response.setData(data);
        response.setMessage("历史成绩获取成功");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search-questions")
    public ResponseEntity<SearchQuestionsResponse> searchQuestions(@RequestBody SearchQuestionsRequest request) {
        SearchQuestionsResponse response = searchQuestionService.searchQuestions(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate-paper")
    public ResponseEntity<Message> generatePaper(@RequestBody GeneratePaperRequest request) {
        Message message = new Message();
        TestPaper testPaper = new TestPaper();
        testPaper.setName(request.getName());
        testPaper.setTotalScore(request.getTotalScore());
        testPaper.setCreatorId(request.getCreatorId());
        testPaper.setDifficulty(request.getDifficulty());
        try {
            testPaperService.insert(testPaper);
            List<PaperQuestion> paperQuestions = getPaperQuestions(request, testPaper);
            paperQuestionService.batchInsert(paperQuestions);

            List<QuestionStatisticDTO> questionStatisticDTOS = new ArrayList<>();
            for (PaperQuestion paperQuestion : paperQuestions) {
                QuestionStatisticDTO dto = new QuestionStatisticDTO();
                dto.setId(paperQuestion.getQuestionId());
                dto.setType(paperQuestion.getQuestionType());
                questionStatisticDTOS.add(dto);
            }
            questionStatisticService.addReferencedCount(questionStatisticDTOS);

            message.setMessage("success");
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
            message.setMessage("Fail:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }


    private static List<PaperQuestion> getPaperQuestions(GeneratePaperRequest request, TestPaper testPaper) {
        List<PaperQuestion> paperQuestions = new ArrayList<>();
        for(GeneratePaperRequest.questionInfo questionInfo : request.getQuestions()) {
            PaperQuestion paperQuestion = new PaperQuestion();
            paperQuestion.setPaperId(testPaper.getId());
            StringBuilder score = new StringBuilder(questionInfo.getScore());
            if (questionInfo.getType().equals("big")) {
                for (String subScore : questionInfo.getSubScores()) {
                    score.append("#").append(subScore);
                }
            }
            paperQuestion.setScore(score.toString());
            paperQuestion.setQuestionId(questionInfo.getId());
            paperQuestion.setQuestionType(questionInfo.getType());
            paperQuestion.setSequence(questionInfo.getSequence());
            paperQuestions.add(paperQuestion);

        }
        return paperQuestions;
    }

    @GetMapping("/papers/{id}")
    public ResponseEntity<GetPapersResponse> getPapers(@PathVariable Long id)
    {
        GetPapersResponse response = new GetPapersResponse();
        List<GetPapersResponse.PaperInfo> infos = new ArrayList<>();
        try {
            List<TestPaper> papers = testPaperService.selectByCreatorId(id);
            for (TestPaper paper : papers) {
                GetPapersResponse.PaperInfo info = new GetPapersResponse.PaperInfo();
                info.setId(paper.getId());
                info.setName(paper.getName());
                info.setDifficulty(paper.getDifficulty());
                info.setTotalScore(paper.getTotalScore());
                Date createTime = paper.getCreateTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = sdf.format(createTime);
                info.setCreateTime(formattedDate);
                infos.add(info);
            }
            response.setPapers(infos);
            response.setMessage("success");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("错误:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/paper")
    public ResponseEntity<GetPaperDetailResponse> getPaper(@RequestParam Long id) throws JsonProcessingException {
        GetPaperDetailResponse response = new GetPaperDetailResponse();
        TestPaper paper = testPaperService.selectById(id);
        response.setTotalScore(paper.getTotalScore());
        List<PaperQuestion> paperQuestions = paperQuestionService.selectByPaperId(id);
        if (paperQuestions == null) {
            response.setMessage("试卷不存在");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<GetPaperDetailResponse.QuestionInfo> infos = new ArrayList<>();
        for (PaperQuestion paperQuestion : paperQuestions) {
            GetPaperDetailResponse.QuestionInfo info = new GetPaperDetailResponse.QuestionInfo();
            info.setSequence(paperQuestion.getSequence());
            if (paperQuestion.getQuestionType().equals("big")) {
                QuestionBody questionBody = questionBodyService.getQuestionBodyById(paperQuestion.getQuestionId());
                info.setBody(questionBody.getBody());

                String[] scores = paperQuestion.getScore().split("#");
                int scoreIndex = 0;
                info.setScore(scores[scoreIndex ++]);
                List<GetPaperDetailResponse.QuestionInfo.SubQuestion> subQuestions = new ArrayList<>();
                List<Question> questions = questionService.getQuestionsByQuestionBodyId(paperQuestion.getQuestionId());
                for (Question question : questions) {
                    GetPaperDetailResponse.QuestionInfo.SubQuestion subQuestion = new GetPaperDetailResponse.QuestionInfo.SubQuestion();
                    subQuestion.setQuestion(question.getContent());
                    subQuestion.setType(question.getType());

                    // 选项
                    if ("CHOICE".equals(question.getType())) {
                        String[] choices = question.getOptions().split("\\$\\$");
                        subQuestion.setOptions(Arrays.stream(choices).toList());
                    }

                    // 答案和解析
                    String[] temps = question.getAnswer().split("\\$\\$");
                    String answer = temps[0];
                    if (question.getType().equals("FILL_IN_BLANK")) {
                        answer = answer.replaceAll("##", ";");
                    }
                    subQuestion.setAnswer(question.getAnswer());
                    if (temps.length > 1) {
                        String explanation = temps[1];
                        subQuestion.setExplanation(explanation);
                    }

                    //分数
                    subQuestion.setScore(scores[scoreIndex ++]);

                    //知识点
                    subQuestion.setKnowledge(knowledgePointService.getKnowledgePointNameById(question.getKnowledgePointId()));
                    subQuestions.add(subQuestion);
                }
                info.setSubQuestions(subQuestions);
            }
            //小题
            else {
                Question question = questionService.getQuestionById(paperQuestion.getQuestionId());
                if (question.getBodyId() != null) {
                    QuestionBody questionBody = questionBodyService.getQuestionBodyById(question.getBodyId());
                    info.setBody(questionBody.getBody());
                }
                info.setQuestion(question.getContent());
                info.setScore(paperQuestion.getScore());
                info.setType(question.getType());

                //选项
                if ("CHOICE".equals(question.getType())) {
                    String[] choices = question.getOptions().split("\\$\\$");
                    info.setOptions(Arrays.stream(choices).toList());
                }

                //答案和解析
                String[] temps = question.getAnswer().split("\\$\\$");
                String answer = temps[0];
                if (question.getType().equals("FILL_IN_BLANK")) {
                    answer = answer.replaceAll("##", ";");
                }
                info.setAnswer(question.getAnswer());
                if (temps.length > 1) {
                    String explanation = temps[1];
                    info.setExplanation(explanation);
                }

                info.setKnowledge(knowledgePointService.getKnowledgePointNameById(question.getKnowledgePointId()));
            }
            infos.add(info);
            response.setQuestions(infos);
            response.setMessage("success");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/uploaded-questions")
    public ResponseEntity<GetUploadedQuestionResponse> getUploadedQuestions(@AuthenticationPrincipal BaseUser user) {
        GetUploadedQuestionResponse response = new GetUploadedQuestionResponse();
        Long teacherId = user.getId();
        System.out.println(teacherId);
        List<UploadQuestion> uploadQuestions = uploadQuestionService.findByTeacherId(teacherId);
        List<Long> smallIds = new ArrayList<>();
        ArrayList<Long> bigIds = new ArrayList<>();

        for (UploadQuestion uploadQuestion : uploadQuestions) {
            if (uploadQuestion.getType().equals("big")) {
                bigIds.add(uploadQuestion.getQuestionId());
            }
            else {
                smallIds.add(uploadQuestion.getQuestionId());
            }
        }

        List<Question> smallQuestions = questionService.getQuestionsByIds(smallIds);
        List<QuestionBody> bigQuestions = questionBodyService.getQuestionBodiesByIds(bigIds);
        List<GetUploadedQuestionResponse.UploadQuestionInfo> infos = new ArrayList<>();

        for (Question question : smallQuestions) {
            GetUploadedQuestionResponse.UploadQuestionInfo info = new GetUploadedQuestionResponse.UploadQuestionInfo();
            if (question.getBodyId() != null) {
                info.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
            }
            info.setContent(question.getContent());
            Date uploadTime = questionStatisticService.getUploadTime(question.getId(), "small");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            info.setUploadTime(sdf.format(uploadTime));
            info.setType(question.getType());

            if (question.getType().equals("CHOICE")) {
                String[] choices = question.getOptions().split("\\$\\$");
                info.setOptions(Arrays.stream(choices).toList());
            }

            String [] temps = question.getAnswer().split("\\$\\$");
            String answer = temps[0];
            if (question.getType().equals("FILL_IN_BLANK")) {
                answer = answer.replaceAll("##", ";");
            }
            info.setAnswer(answer);
            if (temps.length > 1) {
                String explanation = temps[1];
                info.setExplanation(explanation);
            }
            info.setKnowledgePoint(knowledgePointService.getKnowledgePointNameById(question.getKnowledgePointId()));

            infos.add(info);
        }

        for (QuestionBody body : bigQuestions) {
            GetUploadedQuestionResponse.UploadQuestionInfo info = new GetUploadedQuestionResponse.UploadQuestionInfo();
            info.setBody(body.getBody());
            Date uploadTime = questionStatisticService.getUploadTime(body.getId(), "big");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            info.setUploadTime(sdf.format(uploadTime));

            List<GetUploadedQuestionResponse.subQuestion> subQuestions = new ArrayList<>();
            List<Question> questions = questionService.getQuestionsByQuestionBodyId(body.getId());
            for (Question question : questions) {
                GetUploadedQuestionResponse.subQuestion subInfo = new GetUploadedQuestionResponse.subQuestion();
                subInfo.setContent(question.getContent());
                subInfo.setType(question.getType());
                if (question.getType().equals("CHOICE")) {
                    String[] choices = question.getOptions().split("\\$\\$");
                    subInfo.setOptions(Arrays.stream(choices).toList());
                }

                String [] temps = question.getAnswer().split("\\$\\$");
                String answer = temps[0];
                if (question.getType().equals("FILL_IN_BLANK")) {
                    answer = answer.replaceAll("##", ";");
                }
                subInfo.setAnswer(answer);
                if (temps.length > 1) {
                    String explanation = temps[1];
                    subInfo.setExplanation(explanation);
                }
                subInfo.setKnowledgePoint(knowledgePointService.getKnowledgePointNameById(question.getKnowledgePointId()));
                subQuestions.add(subInfo);
            }
            info.setSubQuestions(subQuestions);
            infos.add(info);
        }
        response.setMessage("success");
        response.setUploadedQuestions(infos);
        return ResponseEntity.ok(response);
    }
}

package com.example.controller;

import com.example.dto.mapper.QuestionStatisticDTO;
import com.example.dto.redis.PreAssembledQuestion;
import com.example.dto.redis.SubQuestion;
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
import com.example.model.submission.SubmissionAnswer;
import com.example.model.user.BaseUser;
import com.example.model.user.Teacher;
import com.example.model.view.*;
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
import com.example.service.question.impl.*;
import com.example.service.submission.SubmissionAnswerService;
import com.example.service.submission.impl.SubmissionAnswerServiceImpl;
import com.example.service.view.*;
import com.example.service.submission.AssignmentSubmissionService;
import com.example.service.view.impl.AssignmentScoresViewServiceImpl;
import com.example.service.view.impl.AssignmentStatsViewServiceImpl;
import com.example.service.view.impl.AssignmentStudentViewServiceImpl;
import com.example.service.submission.impl.AssignmentSubmissionServiceImpl;
import com.example.service.user.StatsStudentService;
import com.example.service.user.StudentService;
import com.example.service.user.TeacherService;
import com.example.service.user.impl.StatsStudentServiceImpl;
import com.example.service.user.impl.StudentServiceImpl;
import com.example.service.user.impl.TeacherServiceImpl;
import com.example.service.view.impl.StudentStatsViewServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private final AssignmentRecipientService assignmentRecipientService;
    private final AssignmentStudentViewService assignmentStudentViewService;
    private final StudentStatsViewService studentStatsViewService;
    private final ApproveQuestionService approveQuestionService;
    private final TeacherQuestionStatisticService teacherQuestionStatisticService;
    private final SubmissionAnswerService submissionAnswerService;
    private final AssignmentStatsViewService assignmentStatsViewService;
    private final AssignmentScoresViewService assignmentScoresViewService;
    private final PreAssembledQuestionService preAssembledQuestionService;
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
                                     UploadQuestionServiceImpl uploadQuestionService,
                                     AssignmentRecipientServiceImpl assignmentRecipientService,
                                     AssignmentStudentViewServiceImpl assignmentStudentViewService,
                                     StudentStatsViewServiceImpl studentStatsViewService,
                                     ApproveQuestionService approveQuestionService,
                                     TeacherQuestionStatisticService teacherQuestionStatisticService,
                                     SubmissionAnswerServiceImpl submissionAnswerService,
                                     AssignmentStatsViewServiceImpl assignmentStatsViewService,
                                     AssignmentScoresViewServiceImpl assignmentScoresViewService,
                                     PreAssembledQuestionService preAssembledQuestionService
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
        this.assignmentRecipientService = assignmentRecipientService;
        this.assignmentStudentViewService = assignmentStudentViewService;
        this.studentStatsViewService = studentStatsViewService;
        this.approveQuestionService = approveQuestionService;
        this.teacherQuestionStatisticService = teacherQuestionStatisticService;
        this.submissionAnswerService = submissionAnswerService;
        this.assignmentStatsViewService = assignmentStatsViewService;
        this.assignmentScoresViewService = assignmentScoresViewService;
        this.preAssembledQuestionService = preAssembledQuestionService;
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



    @GetMapping("/get-all-waiting-questions")
    public ResponseEntity<GetAllQuestionsResponse> getAllWaitingQuestions(@AuthenticationPrincipal BaseUser user) {
        GetAllQuestionsResponse response = new GetAllQuestionsResponse();
        List<GetAllQuestionsResponse.infoData> questions = new ArrayList<>();

        Teacher teacher = teacherService.getTeacherById(user.getId());

        try {
            List<UploadQuestion> uploadQuestions = uploadQuestionService.getInSchoolQuestions(teacher.getSchoolId());
            for (UploadQuestion uploadQuestion : uploadQuestions) {
                if (!questionStatisticService.checkQuestionWaiting(uploadQuestion.getQuestionId(), uploadQuestion.getType())) {
                    continue;
                }

                GetAllQuestionsResponse.infoData infoData = new GetAllQuestionsResponse.infoData();
                Long questionId = uploadQuestion.getQuestionId();
                Long teacherId = uploadQuestion.getTeacherId();
                String type = uploadQuestion.getType();
                infoData.setId(uploadQuestion.getId());
                infoData.setQuestionId(questionId);
                infoData.setType(type);
                LocalDateTime uploadTime = questionStatisticService.getUploadTime(questionId, type);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = uploadTime.format(formatter);
                infoData.setUploadTime(formattedDate);
                infoData.setUploadTeacher(teacherService.getTeacherNameById(teacherId));
                questions.add(infoData);
            }
            response.setMessage("获取题目成功");
            response.setQuestions(questions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("获取题目失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/get-all-access-questions")
    public ResponseEntity<GetAllQuestionsResponse> getALLAccessQuestions(@AuthenticationPrincipal BaseUser user) {
        GetAllQuestionsResponse response = new GetAllQuestionsResponse();
        List<GetAllQuestionsResponse.infoData> questions = new ArrayList<>();

        Teacher teacher = teacherService.getTeacherById(user.getId());

        try {
            List<UploadQuestion> uploadQuestions = uploadQuestionService.getInSchoolQuestions(teacher.getSchoolId());
            for (UploadQuestion uploadQuestion : uploadQuestions) {
                if (!questionStatisticService.checkQuestionPassed(uploadQuestion.getQuestionId(), uploadQuestion.getType())) {
                    continue;
                }

                GetAllQuestionsResponse.infoData infoData = new GetAllQuestionsResponse.infoData();
                Long questionId = uploadQuestion.getQuestionId();
                Long teacherId = uploadQuestion.getTeacherId();
                String type = uploadQuestion.getType();
                infoData.setId(uploadQuestion.getId());
                infoData.setQuestionId(questionId);
                infoData.setType(type);
                LocalDateTime uploadTime = questionStatisticService.getUploadTime(questionId, type);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = uploadTime.format(formatter);
                infoData.setUploadTime(formattedDate);
                infoData.setUploadTime(formattedDate);
                infoData.setUploadTeacher(teacherService.getTeacherNameById(teacherId));
                questions.add(infoData);
            }
            response.setMessage("获取题目成功");
            response.setQuestions(questions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("获取题目失败：" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/get-question")
    public ResponseEntity<GetQuestionResponse> getQuestion(@AuthenticationPrincipal BaseUser user,
                                                           @RequestParam Long questionId,
                                                           @RequestParam String type) throws JsonProcessingException {
        GetQuestionResponse response = new GetQuestionResponse();

        if (type.equals("small")) {
            Question question = questionService.getQuestionById(questionId);
            if (question.getBodyId() != null) {
                QuestionBody questionBody = questionBodyService.getQuestionBodyById(question.getBodyId());
                String body = questionBody.getBody();
                response.setBody(body);
            }
            response.setType(question.getType());
            String content = question.getContent();
            response.setContent(content);

            if (question.getType().equals("CHOICE")) {
                List<String> options = Arrays.asList(question.getOptions().split("\\$\\$"));
                response.setOptions(options);
            }
            String[] temps = question.getAnswer().split("\\$\\$");
            String answer = temps[0];
            if (question.getType().equals("FILL_IN_BLANK")) {
                answer = answer.replaceAll("##", ";");
            }
            response.setAnswer(answer);
            if (temps.length > 1){
                response.setExplanation(temps[1]);
            }

            String knowledgePoint = knowledgePointService.getKnowledgePointNameById(question.getKnowledgePointId());
            response.setKnowledgePoint(knowledgePoint);
        }
        else {
            QuestionBody questionBody = questionBodyService.getQuestionBodyById(questionId);
            String body = questionBody.getBody();
            response.setBody(body);
            List<Question> subQuestions = questionService.getQuestionsByQuestionBodyId(questionId);

            List<GetQuestionResponse.subQuestion> subQuestionList = new ArrayList<>();
            for (Question subQ : subQuestions) {
                GetQuestionResponse.subQuestion subQuestion = new GetQuestionResponse.subQuestion();
                subQuestion.setContent(subQ.getContent());
                subQuestion.setType(subQ.getType());
                if ("CHOICE".equals(subQ.getType())) {
                    subQuestion.setOptions(Arrays.asList(subQ.getOptions().split("\\$\\$")));
                }

                String[] temps = subQ.getAnswer().split("\\$\\$");
                String answer = temps[0];
                if ("FILL_IN_BLANK".equals(subQ.getType())) {
                    answer = answer.replaceAll("##", ";");
                }
                subQuestion.setAnswer(answer);
                if (temps.length > 1) {
                    subQuestion.setExplanation(temps[1]);
                }

                String knowledgePoint = knowledgePointService.getKnowledgePointNameById(subQ.getKnowledgePointId());
                subQuestion.setKnowledgePoint(knowledgePoint);
                subQuestionList.add(subQuestion);
            }
            response.setSubQuestions(subQuestionList);
        }

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


    @DeleteMapping("/delete-question")
    public ResponseEntity<Message> deleteQuestion(@RequestParam Long questionId, @RequestParam String type) throws JsonProcessingException {
        Message response = new Message();
        if(Objects.equals(type, "small")){
            Question question = questionService.getQuestionById(questionId);
            if(question == null){
                response.setMessage("问题不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            questionService.deleteQuestion(question);
        }
        else if(Objects.equals(type, "big")){
            QuestionBody questionBody = questionBodyService.getQuestionBodyById(questionId);
            if(questionBody == null){
                response.setMessage("问题不存在");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            questionBodyService.deleteQuestionBody(questionId);
            cacheRefreshService.markTypeCacheOutOfDate(questionBody.getType());
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
                LocalDateTime createTime = paper.getCreateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = createTime.format(formatter);
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
        try{
            List<TeacherQuestionStatistic> teacherQuestionStatistics = teacherQuestionStatisticService.getStatisticsByTeacherId(user.getId());
            List<GetUploadedQuestionResponse.UploadQuestionInfo> uploadQuestions = new ArrayList<>();
            for (TeacherQuestionStatistic teacherQuestionStatistic : teacherQuestionStatistics) {
                GetUploadedQuestionResponse.UploadQuestionInfo uploadQuestionInfo = new GetUploadedQuestionResponse.UploadQuestionInfo();
                uploadQuestionInfo.setQuestionId(teacherQuestionStatistic.getQuestionId());
                uploadQuestionInfo.setType(teacherQuestionStatistic.getQuestionType());
                LocalDateTime uploadTime = teacherQuestionStatistic.getUploadTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = uploadTime.format(formatter);
                uploadQuestionInfo.setUploadTime(formattedDate);
                uploadQuestionInfo.setComment(teacherQuestionStatistic.getComment());
                uploadQuestionInfo.setStatus(teacherQuestionStatistic.getStatus());
                uploadQuestionInfo.setExecuteTeacher(teacherQuestionStatistic.getExecuteTeacher());
                uploadQuestions.add(uploadQuestionInfo);
            }
            response.setUploadedQuestions(uploadQuestions);
            response.setMessage("success");
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            response.setMessage("fail");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/homework/publish")
    public ResponseEntity<Message> publishHomework(@RequestBody PublishHomeworkRequest request, @AuthenticationPrincipal BaseUser user) throws JsonProcessingException {
        Teacher teacher = teacherService.getTeacherById(user.getId());
        Assignment assignment = new Assignment();
        assignment.setTitle(request.getName());
        assignment.setPaperId(request.getReferencedPaperId());
        assignment.setDescription(request.getDescription());
        assignment.setCreatorId(teacher.getId());
        assignment.setEndTime(request.getDueTime());
        assignment.setStartTime(request.getPublishTime());
        try {
            assignmentService.createAssignment(assignment);

            String type = request.getTargetType();
            List<AssignmentRecipient> assignmentRecipientList = new ArrayList<>();
            for (Long targetId : request.getTargetIds()) {
                AssignmentRecipient assignmentRecipient = new AssignmentRecipient();
                assignmentRecipient.setAssignmentId(assignment.getId());
                assignmentRecipient.setRecipientId(targetId);
                if (type.equals("班级")) {
                    assignmentRecipient.setRecipientType("CLASS");
                }
                else if (type.equals("小组")) {
                    assignmentRecipient.setRecipientType("GROUP");
                }
                else {
                    assignmentRecipient.setRecipientType("STUDENT");
                }
                assignmentRecipientList.add(assignmentRecipient);
            }
            assignmentRecipientService.batchInsert(assignmentRecipientList);
            return ResponseEntity.ok(new Message("success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("发布失败"));
        }
    }

    @DeleteMapping("/delete-paper/{id}")
    public ResponseEntity<Message> deletePaper(@AuthenticationPrincipal BaseUser user, @PathVariable Long id) throws JsonProcessingException {
        TestPaper testPaper = testPaperService.selectById(id);
        if (testPaper == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("试卷不存在"));
        }
        if (!testPaper.getCreatorId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("无权限删除试卷"));
        }
        testPaperService.delete(id);
        return ResponseEntity.ok(new Message("success"));
    }

    @PutMapping("/deny-upload-question")
    public ResponseEntity<Message> denyUploadQuestion(@AuthenticationPrincipal BaseUser user,
                                                      @RequestBody DenyUploadQuestionRequest request) throws JsonProcessingException {

        Long executeTeacherId = user.getId();
        Long id = request.getId();
        String comment = request.getComment();

        UploadQuestion uploadQuestion = uploadQuestionService.findById(id);
        if (uploadQuestion.getType().equals("small")) {
            Question question = questionService.getQuestionById(uploadQuestion.getQuestionId());
            questionService.deny(question);
        }
        else {
            QuestionBody questionBody = questionBodyService.getQuestionBodyById(uploadQuestion.getQuestionId());
            questionBodyService.deny(questionBody);
        }

        ApproveQuestion approveQuestion = new ApproveQuestion();
        approveQuestion.setUploadId(id);
        approveQuestion.setStatus("rejected");
        if (comment != null && !comment.isEmpty()) {
            approveQuestion.setComment(comment);
        }
        approveQuestion.setExecuteTeacherId(executeTeacherId);
        approveQuestionService.insert(approveQuestion);
        return ResponseEntity.ok(new Message("success"));
    }

    @GetMapping("/{id}/get-assignment-list")
    public ResponseEntity<AssignmentListResponse> getAssignmentList(@PathVariable Long id) {
        AssignmentListResponse response = new AssignmentListResponse();
        List<AssignmentListResponse.infoData> data = new ArrayList<>();
        List<Assignment> assignments = assignmentService.getAssignmentsByTeacherId(id);
        for (Assignment assignment : assignments) {
            AssignmentListResponse.infoData infoData = new AssignmentListResponse.infoData();
            infoData.setAssignmentId(assignment.getId());
            infoData.setAssignmentTitle(assignment.getTitle());
            infoData.setAssignmentDescription(assignment.getDescription());
            infoData.setStartTime(assignment.getStartTime().toString());
            infoData.setEndTime(assignment.getEndTime().toString());
            infoData.setPaperId(assignment.getPaperId());
            data.add(infoData);
        }
        data.sort(Comparator.comparing(AssignmentListResponse.infoData::getEndTime).reversed());
        response.setData(data);
        response.setMessage("作业列表获取成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}/get-submission-list")
    public ResponseEntity<GetSubmissionListResponse> getSubmissionList(@PathVariable Long id, @RequestParam Long assignmentId) {
        GetSubmissionListResponse response = new GetSubmissionListResponse();
        List<GetSubmissionListResponse.infoData> data = new ArrayList<>();
        List<AssignmentStudentView> assignmentStudentViews = assignmentStudentViewService.selectByAssignmentId(assignmentId);
        for (AssignmentStudentView assignmentStudentView : assignmentStudentViews) {
            GetSubmissionListResponse.infoData infoData = new GetSubmissionListResponse.infoData();
            infoData.setStudentId(assignmentStudentView.getStudentId());
            infoData.setStudentName(assignmentStudentView.getStudentName());
            AssignmentSubmission assignmentSubmission = assignmentSubmissionService.selectByAssignmentIdAndStudentId(assignmentId, assignmentStudentView.getStudentId());
            if(assignmentSubmission == null){
                infoData.setIsSubmitted(0);
                infoData.setSubmitTime(null);
                infoData.setTotalScore(0);
                infoData.setIsMarked(1);
            }
            else if(assignmentSubmission.getSubmitTime() == null){
                infoData.setSubmitTime(null);
                infoData.setTotalScore(0);
                infoData.setIsMarked(1);
            }
            else{
                infoData.setIsSubmitted(1);
                infoData.setSubmitTime(assignmentSubmission.getSubmitTime().toString());
                if(assignmentSubmission.getTotalScore() != null){
                    infoData.setTotalScore(assignmentSubmission.getTotalScore());
                    infoData.setIsMarked(2);
                }
                else{
                    infoData.setTotalScore(null);
                    infoData.setIsMarked(0);
                }
            }
            data.add(infoData);
        }
        response.setMessage("作业提交列表获取成功");
        response.setAssignmentId(assignmentId);
        response.setData(data);
        return ResponseEntity.ok(response);
    }


    /**
     * 批准题目
     * 考虑题目内容更新
     * 添加approve_question表
     */

    @PutMapping("/approve-question")
    public ResponseEntity<Message> approveQuestion(@AuthenticationPrincipal BaseUser user, @RequestBody ApproveQuestionRequest request) throws JsonProcessingException {
        Long executeTeacherId = user.getId();
        Long id = request.getId();
        UploadQuestion uploadQuestion = uploadQuestionService.findById(id);

        /*处理单题批准 questions数量为1 */

        if (uploadQuestion.getType().equals("small")) {
            Question question = questionService.getQuestionById(uploadQuestion.getQuestionId());

            if(request.getQuestions().size() != 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("题目数量错误"));
            }
            ApproveQuestionRequest.QuestionInfo info = request.getQuestions().get(0);
            question.setContent(info.getProblem());
            if (question.getType().equals("CHOICE")) {
                StringBuilder choices = new StringBuilder();
                for (int i = 0; i < info.getChoices().size(); i ++ ){
                    choices.append(info.getChoices().get(i));
                    if (i != info.getChoices().size() - 1) {
                        choices.append("\\$\\$");
                    }
                }
                question.setOptions(choices.toString());
            }
            String temp = info.getAnswer();
            if (question.getType().equals("FILL_IN_BLANK")) {
                temp = temp.replace(";", "##");
            }
            question.setAnswer(info.getAnswer() + "$$" + info.getAnalysis());
            questionService.updateQuestion(question);
            questionService.access(question);
        }

        //大题
        else {
            QuestionBody questionBody = questionBodyService.getQuestionBodyById(uploadQuestion.getQuestionId());
            if(request.getBody() != null && !request.getBody().isEmpty()) {
                questionBody.setBody(request.getBody());
            }
            List<Question> questions = questionService.getQuestionsByQuestionBodyId(questionBody.getId());
            if (questions.size() != request.getQuestions().size()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("题目数量错误"));
            }
            for (int i = 0; i < questions.size(); i++) {
                ApproveQuestionRequest.QuestionInfo info = request.getQuestions().get(i);
                Question question = questions.get(i);
                question.setContent(info.getProblem());
                if (question.getType().equals("CHOICE")) {
                    StringBuilder choices = new StringBuilder();
                    for (int j = 0; j < info.getChoices().size(); j ++ ){
                        choices.append(info.getChoices().get(j));
                        if (j!= info.getChoices().size() - 1) {
                            choices.append("\\$\\$");
                        }
                    }
                    question.setOptions(choices.toString());
                }
                String temp = info.getAnswer();
                if (question.getType().equals("FILL_IN_BLANK")) {
                    temp = temp.replace(";", "##");
                }
                question.setAnswer(info.getAnswer() + "$$" + info.getAnalysis());
                questionService.updateQuestion(question);
            }
            questionBodyService.updateQuestionBody(questionBody);
            questionBodyService.access(questionBody);
        }
        ApproveQuestion approveQuestion = new ApproveQuestion();
        approveQuestion.setExecuteTeacherId(executeTeacherId);
        approveQuestion.setComment(request.getComment());
        approveQuestion.setStatus(ApproveQuestion.APPROVE);
        approveQuestion.setUploadId(id);
        approveQuestionService.insert(approveQuestion);

        return ResponseEntity.ok(new Message("题目批准成功"));
    }

    @PutMapping("/modify-question")
    public ResponseEntity<Message> modifyQuestion(@AuthenticationPrincipal BaseUser user, @RequestBody ModifyQuestionRequest request) throws JsonProcessingException {
        Long executeTeacherId = user.getId();
        Long id = request.getId();
        UploadQuestion uploadQuestion = uploadQuestionService.findById(id);

        /*处理单题批准 questions数量为1 */

        if (uploadQuestion.getType().equals("small")) {
            Question question = questionService.getQuestionById(uploadQuestion.getQuestionId());

            if (request.getQuestions().size() != 1) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("题目数量错误"));
            }
            ModifyQuestionRequest.QuestionInfo info = request.getQuestions().get(0);
            question.setContent(info.getProblem());
            if (question.getType().equals("CHOICE")) {
                StringBuilder choices = new StringBuilder();
                for (int i = 0; i < info.getChoices().size(); i++) {
                    choices.append(info.getChoices().get(i));
                    if (i != info.getChoices().size() - 1) {
                        choices.append("\\$\\$");
                    }
                }
                question.setOptions(choices.toString());
            }
            String temp = info.getAnswer();
            if (question.getType().equals("FILL_IN_BLANK")) {
                temp = temp.replace(";", "##");
            }
            question.setAnswer(info.getAnswer() + "$$" + info.getAnalysis());
            questionService.updateQuestion(question);
            questionService.access(question);
        }

        //大题
        else {
            QuestionBody questionBody = questionBodyService.getQuestionBodyById(uploadQuestion.getQuestionId());
            if (request.getBody() != null && !request.getBody().isEmpty()) {
                questionBody.setBody(request.getBody());
            }
            List<Question> questions = questionService.getQuestionsByQuestionBodyId(questionBody.getId());
            if (questions.size() != request.getQuestions().size()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("题目数量错误"));
            }
            for (int i = 0; i < questions.size(); i++) {
                ModifyQuestionRequest.QuestionInfo info = request.getQuestions().get(i);
                Question question = questions.get(i);
                question.setContent(info.getProblem());
                if (question.getType().equals("CHOICE")) {
                    StringBuilder choices = new StringBuilder();
                    for (int j = 0; j < info.getChoices().size(); j++) {
                        choices.append(info.getChoices().get(j));
                        if (j != info.getChoices().size() - 1) {
                            choices.append("\\$\\$");
                        }
                    }
                    question.setOptions(choices.toString());
                }
                String temp = info.getAnswer();
                if (question.getType().equals("FILL_IN_BLANK")) {
                    temp = temp.replace(";", "##");
                }
                question.setAnswer(info.getAnswer() + "$$" + info.getAnalysis());
                questionService.updateQuestion(question);
            }
            questionBodyService.updateQuestionBody(questionBody);
            questionBodyService.access(questionBody);
        }

        return ResponseEntity.ok(new Message("题目修改成功"));
    }
    @GetMapping("{id}/get-submission")
    public ResponseEntity<GetSubmissionResponse> getSubmission(@PathVariable Long id, @RequestParam Long assignmentId, @RequestParam Long studentId) {
        GetSubmissionResponse response = new GetSubmissionResponse();
        List<GetSubmissionResponse.QuestionInfo> data = new ArrayList<>();
        AssignmentSubmission assignmentSubmission = assignmentSubmissionService.selectByAssignmentIdAndStudentId(assignmentId, studentId);
        int totalScore = 0;
        if(assignmentSubmission != null){
            List<SubmissionAnswer> submissionAnswers = submissionAnswerService.selectBySubmissionId(assignmentSubmission.getId());
            for(int i = 0; i < submissionAnswers.size(); i++){
                SubmissionAnswer submissionAnswer = submissionAnswers.get(i);
                GetSubmissionResponse.QuestionInfo questionInfo = new GetSubmissionResponse.QuestionInfo();
                String [] sequenceTemp = submissionAnswer.getSequence().split("\\.");
                questionInfo.setSequence(Integer.parseInt(sequenceTemp[0]));
                if(sequenceTemp.length > 1){
                    int scoreTemp = 0;
                    List<GetSubmissionResponse.SubQuestionInfo> subQuestions = new ArrayList<>();
                    GetSubmissionResponse.SubQuestionInfo subQuestionInfo = new GetSubmissionResponse.SubQuestionInfo();
                    Question question;
                    try {
                        question = questionService.getQuestionById(submissionAnswer.getQuestionId());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    QuestionBody questionBody = questionBodyService.getQuestionBodyById(question.getBodyId());
                    questionInfo.setBody(questionBody.getBody());
                    subQuestionInfo.setQuestion(question.getContent());
                    String [] answerAndExplanation = question.getAnswer().split("\\$\\$");
                    subQuestionInfo.setAnswer(answerAndExplanation[0].replace("##", ";"));
                    if(answerAndExplanation.length > 1){
                        subQuestionInfo.setExplanation(answerAndExplanation[1]);
                    }
                    subQuestionInfo.setType(question.getType());
                    if(question.getType().equals("CHOICE")){
                        subQuestionInfo.setOptions(drawOptions(question.getOptions()));
                        if(submissionAnswer.getScore() == null){
                            StatsStudent statsStudent = statsStudentService.selectByStudentIdAndKnowledgePointId(studentId, question.getKnowledgePointId());
                            if(submissionAnswer.getAnswerContent().equals(answerAndExplanation[0])){
                                submissionAnswer.setScore(submissionAnswer.getQuestionScore());
                                if(statsStudent != null){
                                    statsStudent.setTotalScore(statsStudent.getTotalScore() + 100);
                                    statsStudent.setScore(statsStudent.getScore() + 100);
                                    statsStudentService.updateStatsStudent(statsStudent);
                                }
                                else{
                                    StatsStudent statsStudentNew = new StatsStudent();
                                    statsStudentNew.setStudentId(studentId);
                                    statsStudentNew.setKnowledgePointId(question.getKnowledgePointId());
                                    statsStudentNew.setTotalScore(100L);
                                    statsStudentNew.setScore(100L);
                                    statsStudentService.addStatsStudent(statsStudentNew);
                                }

                                QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(question.getId(), "small");
                                questionStatistic.setTotalScore(questionStatistic.getTotalScore() + 100L);
                                questionStatistic.setCompleteCount(questionStatistic.getCompleteCount() + 1);
                                questionStatisticService.update(questionStatistic);
                                if(question.getBodyId() != null){
                                    QuestionStatistic questionBodyStatistic = questionStatisticService.findByIdAndType(question.getBodyId(), "big");
                                    questionBodyStatistic.setTotalScore(questionBodyStatistic.getTotalScore() + 100L);
                                    questionBodyStatistic.setCompleteCount(questionBodyStatistic.getCompleteCount() + 1);
                                    questionStatisticService.update(questionBodyStatistic);
                                }

                            }
                            else {
                                submissionAnswer.setScore(0);
                                if(statsStudent != null){
                                    statsStudent.setTotalScore(statsStudent.getTotalScore() + 100);
                                    statsStudentService.updateStatsStudent(statsStudent);
                                }
                                else{
                                    StatsStudent statsStudentNew = new StatsStudent();
                                    statsStudentNew.setStudentId(studentId);
                                    statsStudentNew.setKnowledgePointId(question.getKnowledgePointId());
                                    statsStudentNew.setTotalScore(100L);
                                    statsStudentNew.setScore(0L);
                                    statsStudentService.addStatsStudent(statsStudentNew);
                                }

                                QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(question.getId(), "small");
                                questionStatistic.setCompleteCount(questionStatistic.getCompleteCount() + 1);
                                questionStatisticService.update(questionStatistic);
                                if(question.getBodyId() != null){
                                    QuestionStatistic questionBodyStatistic = questionStatisticService.findByIdAndType(question.getBodyId(), "big");
                                    questionBodyStatistic.setCompleteCount(questionBodyStatistic.getCompleteCount() + 1);
                                    questionStatisticService.update(questionBodyStatistic);
                                }

                            }
                            submissionAnswerService.update(submissionAnswer);
                        }
                    }
                    subQuestionInfo.setSubScore(submissionAnswer.getQuestionScore());
                    scoreTemp += submissionAnswer.getQuestionScore();
                    totalScore += submissionAnswer.getQuestionScore();
                    subQuestionInfo.setStudentAnswer(submissionAnswer.getAnswerContent());
                    subQuestionInfo.setSubmissionAnswerId(submissionAnswer.getId());
                    subQuestions.add(subQuestionInfo);
                    while(true){
                        i ++;
                        if(i >= submissionAnswers.size()){
                            questionInfo.setScore(scoreTemp);
                            questionInfo.setSubQuestions(subQuestions);
                            data.add(questionInfo);
                            break;
                        }
                        submissionAnswer = submissionAnswers.get(i);
                        String [] temp = submissionAnswer.getSequence().split("\\.");
                        if(Objects.equals(temp[0], sequenceTemp[0])){
                            Question questionTemp;
                            try {
                                questionTemp = questionService.getQuestionById(submissionAnswer.getQuestionId());
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            GetSubmissionResponse.SubQuestionInfo subQuestionInfoTemp = new GetSubmissionResponse.SubQuestionInfo();
                            subQuestionInfoTemp.setQuestion(questionTemp.getContent());
                            String [] answerAndExplanationTemp = questionTemp.getAnswer().split("\\$\\$");
                            subQuestionInfoTemp.setAnswer(answerAndExplanationTemp[0].replace("##", ";"));
                            if(answerAndExplanationTemp.length > 1){
                                subQuestionInfoTemp.setExplanation(answerAndExplanationTemp[1]);
                            }
                            subQuestionInfoTemp.setType(questionTemp.getType());
                            if(questionTemp.getType().equals("CHOICE")){
                                subQuestionInfoTemp.setOptions(drawOptions(questionTemp.getOptions()));
                                if(submissionAnswer.getScore() == null){
                                    StatsStudent statsStudent = statsStudentService.selectByStudentIdAndKnowledgePointId(studentId, questionTemp.getKnowledgePointId());
                                    if(submissionAnswer.getAnswerContent().equals(answerAndExplanation[0])){
                                        submissionAnswer.setScore(submissionAnswer.getQuestionScore());
                                        if(statsStudent != null){
                                            statsStudent.setTotalScore(statsStudent.getTotalScore() + 100);
                                            statsStudent.setScore(statsStudent.getScore() + 100);
                                            statsStudentService.updateStatsStudent(statsStudent);
                                        }
                                        else{
                                            StatsStudent statsStudentNew = new StatsStudent();
                                            statsStudentNew.setStudentId(studentId);
                                            statsStudentNew.setKnowledgePointId(questionTemp.getKnowledgePointId());
                                            statsStudentNew.setTotalScore(100L);
                                            statsStudentNew.setScore(100L);
                                            statsStudentService.addStatsStudent(statsStudentNew);
                                        }

                                        QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(questionTemp.getId(), "small");
                                        questionStatistic.setTotalScore(questionStatistic.getTotalScore() + 100L);
                                        questionStatistic.setCompleteCount(questionStatistic.getCompleteCount() + 1);
                                        questionStatisticService.update(questionStatistic);
                                        if(questionTemp.getBodyId() != null){
                                            QuestionStatistic questionBodyStatistic = questionStatisticService.findByIdAndType(questionTemp.getBodyId(), "big");
                                            questionBodyStatistic.setTotalScore(questionBodyStatistic.getTotalScore() + 100L);
                                            questionBodyStatistic.setCompleteCount(questionBodyStatistic.getCompleteCount() + 1);
                                            questionStatisticService.update(questionBodyStatistic);
                                        }

                                    }
                                    else {
                                        submissionAnswer.setScore(0);
                                        if(statsStudent != null){
                                            statsStudent.setTotalScore(statsStudent.getTotalScore() + 100);
                                            statsStudentService.updateStatsStudent(statsStudent);
                                        }
                                        else{
                                            StatsStudent statsStudentNew = new StatsStudent();
                                            statsStudentNew.setStudentId(studentId);
                                            statsStudentNew.setKnowledgePointId(questionTemp.getKnowledgePointId());
                                            statsStudentNew.setTotalScore(100L);
                                            statsStudentNew.setScore(0L);
                                            statsStudentService.addStatsStudent(statsStudentNew);
                                        }

                                        QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(questionTemp.getId(), "small");
                                        questionStatistic.setCompleteCount(questionStatistic.getCompleteCount() + 1);
                                        questionStatisticService.update(questionStatistic);
                                        if(questionTemp.getBodyId() != null){
                                            QuestionStatistic questionBodyStatistic = questionStatisticService.findByIdAndType(questionTemp.getBodyId(), "big");
                                            questionBodyStatistic.setCompleteCount(questionBodyStatistic.getCompleteCount() + 1);
                                            questionStatisticService.update(questionBodyStatistic);
                                        }

                                    }
                                    submissionAnswerService.update(submissionAnswer);
                                }
                            }
                            subQuestionInfoTemp.setSubScore(submissionAnswer.getQuestionScore());
                            scoreTemp += submissionAnswer.getQuestionScore();
                            totalScore += submissionAnswer.getQuestionScore();
                            subQuestionInfoTemp.setStudentAnswer(submissionAnswer.getAnswerContent());
                            subQuestionInfoTemp.setSubmissionAnswerId(submissionAnswer.getId());
                            subQuestions.add(subQuestionInfoTemp);
                        }
                        else {
                            i --;
                            questionInfo.setScore(scoreTemp);
                            questionInfo.setSubQuestions(subQuestions);
                            data.add(questionInfo);
                            break;
                        }
                    }
                }
                else {
                    Question question = new Question();
                    try {
                        question = questionService.getQuestionById(submissionAnswer.getQuestionId());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    if(question.getBodyId() != null){
                        questionInfo.setBody(questionBodyService.getQuestionBodyById(question.getBodyId()).getBody());
                    }
                    questionInfo.setSubmissionAnswerId(submissionAnswer.getId());
                    questionInfo.setQuestion(question.getContent());
                    String [] answerAndExplanation = question.getAnswer().split("\\$\\$");
                    questionInfo.setAnswer(answerAndExplanation[0].replace("##", ";"));
                    if(answerAndExplanation.length > 1){
                        questionInfo.setExplanation(answerAndExplanation[1]);
                    }
                    questionInfo.setType(question.getType());
                    if(question.getType().equals("CHOICE")){
                        questionInfo.setOptions(drawOptions(question.getOptions()));
                        if(submissionAnswer.getScore() == null){
                            StatsStudent statsStudent = statsStudentService.selectByStudentIdAndKnowledgePointId(studentId, question.getKnowledgePointId());
                            if(submissionAnswer.getAnswerContent().equals(answerAndExplanation[0])){
                                submissionAnswer.setScore(submissionAnswer.getQuestionScore());
                                if(statsStudent != null){
                                    statsStudent.setTotalScore(statsStudent.getTotalScore() + 100);
                                    statsStudent.setScore(statsStudent.getScore() + 100);
                                    statsStudentService.updateStatsStudent(statsStudent);
                                }
                                else{
                                    StatsStudent statsStudentNew = new StatsStudent();
                                    statsStudentNew.setStudentId(studentId);
                                    statsStudentNew.setKnowledgePointId(question.getKnowledgePointId());
                                    statsStudentNew.setTotalScore(100L);
                                    statsStudentNew.setScore(100L);
                                    statsStudentService.addStatsStudent(statsStudentNew);
                                }

                                QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(question.getId(), "small");
                                questionStatistic.setTotalScore(questionStatistic.getTotalScore() + 100L);
                                questionStatistic.setCompleteCount(questionStatistic.getCompleteCount() + 1);
                                questionStatisticService.update(questionStatistic);
                                if(question.getBodyId() != null){
                                    QuestionStatistic questionBodyStatistic = questionStatisticService.findByIdAndType(question.getBodyId(), "big");
                                    questionBodyStatistic.setTotalScore(questionBodyStatistic.getTotalScore() + 100L);
                                    questionBodyStatistic.setCompleteCount(questionBodyStatistic.getCompleteCount() + 1);
                                    questionStatisticService.update(questionBodyStatistic);
                                }

                            }
                            else {
                                submissionAnswer.setScore(0);
                                if(statsStudent != null){
                                    statsStudent.setTotalScore(statsStudent.getTotalScore() + 100);
                                    statsStudentService.updateStatsStudent(statsStudent);
                                }
                                else{
                                    StatsStudent statsStudentNew = new StatsStudent();
                                    statsStudentNew.setStudentId(studentId);
                                    statsStudentNew.setKnowledgePointId(question.getKnowledgePointId());
                                    statsStudentNew.setTotalScore(100L);
                                    statsStudentNew.setScore(0L);
                                    statsStudentService.addStatsStudent(statsStudentNew);
                                }

                                QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(question.getId(), "small");
                                questionStatistic.setCompleteCount(questionStatistic.getCompleteCount() + 1);
                                questionStatisticService.update(questionStatistic);
                                if(question.getBodyId() != null){
                                    QuestionStatistic questionBodyStatistic = questionStatisticService.findByIdAndType(question.getBodyId(), "big");
                                    questionBodyStatistic.setCompleteCount(questionBodyStatistic.getCompleteCount() + 1);
                                    questionStatisticService.update(questionBodyStatistic);
                                }

                            }
                            submissionAnswerService.update(submissionAnswer);
                        }
                    }
                    questionInfo.setStudentAnswer(submissionAnswer.getAnswerContent());
                    questionInfo.setScore(submissionAnswer.getQuestionScore());
                    totalScore += submissionAnswer.getQuestionScore();
                    data.add(questionInfo);
                }
            }
        }
        response.setTotalScore(totalScore);
        response.setQuestions(data);
        response.setMessage("success");
        return ResponseEntity.ok(response);

    }

    @PostMapping("/generate-paper-with-types")
    public ResponseEntity<GeneratePaperWithTypesResponse> generatePaperWithTypes(@AuthenticationPrincipal BaseUser user,
                                                                               @RequestBody GeneratePaperWithTypeRequest request)
                                                                        throws JsonProcessingException {
        GeneratePaperWithTypesResponse response = new GeneratePaperWithTypesResponse();
        List<GeneratePaperWithTypesResponse.QuestionInfo> infos = new ArrayList<>();

        for (GeneratePaperWithTypeRequest.Type type : request.getTypes()) {
            String knowledgeType = type.getType();
            List<PreAssembledQuestion> questions = preAssembledQuestionService.getPreAssembledQuestionsByType(knowledgeType);
            int number = type.getNumber();

            // 高效随机抽取题目 number道
            Collections.shuffle(questions);
            List<PreAssembledQuestion> selectedQuestions = questions.stream()
                    .limit(number)
                    .toList();
            for (PreAssembledQuestion question : selectedQuestions) {
                GeneratePaperWithTypesResponse.QuestionInfo info = new GeneratePaperWithTypesResponse.QuestionInfo();
                info.setId(question.getId());
                info.setBody(question.getQuestionBody());
                List<GeneratePaperWithTypesResponse.SubQuestion> subQuestions = new ArrayList<>();
                for (SubQuestion subQuestion : question.getSubQuestions()) {
                    GeneratePaperWithTypesResponse.SubQuestion subInfo = new GeneratePaperWithTypesResponse.SubQuestion();
                    subInfo.setAnswer(subQuestion.getQuestionAnswer());
                    subInfo.setContent(subQuestion.getQuestionContent());
                    subInfo.setExplanation(subQuestion.getQuestionExplanation());
                    subInfo.setKnowledgePoint(subQuestion.getKnowledgePoint());
                    if (subQuestion.getType().equals("CHOICE")) {
                        subInfo.setOptions(List.of(subQuestion.getQuestionOptions().split("\\$\\$")));
                    }
                    subInfo.setType(subQuestion.getType());
                    subQuestions.add(subInfo);
                }
                QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(question.getId(), "big");
                if (questionStatistic.getCompleteCount() != 0) {
                    info.setDifficulty(questionStatistic.getTotalScore() / questionStatistic.getCompleteCount());
                }
                else {
                    info.setDifficulty(-1.0);
                }
                info.setQuestions(subQuestions);
                infos.add(info);
            }
        }
        response.setQuestions(infos);
        response.setMessage("success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paper/auto")
    public ResponseEntity<AutoPaperResponse> autoPaper(@AuthenticationPrincipal BaseUser user) throws JsonProcessingException {
        //此处硬编码考虑优化

        /*
          积累与运用的小题 knowledgeName = {"字音", "字形", "语言运用", "病句", "信息的提取与概括", "古诗文默写"};
         */
        Long[] knowledgeIds = {102L, 103L, 8L, 7L, 9L, 13L};
        String[] typeNames = {"非连续文本", "文学性文本阅读", "古代诗歌鉴赏", "文言文阅读", "名著阅读"};

        AutoPaperResponse response = new AutoPaperResponse();
        List<AutoPaperResponse.Question> questionInfos = new ArrayList<>(); //积累与运用
        List<AutoPaperResponse.BigQuestion> bigQuestionInfos = new ArrayList<>(); //后续大题

        for (Long knowledgeId : knowledgeIds) {
            List<Question> questions = questionService.getQuestionsByKnowledgePointId(knowledgeId);
            if (questions == null || questions.isEmpty()) continue;
            // 随机抽取一道
            Collections.shuffle(questions);
            Question question = questions.get(0);
            AutoPaperResponse.Question questionInfo = new AutoPaperResponse.Question();
            questionInfo.setId(question.getId());
            questionInfo.setContent(question.getContent());
            questionInfo.setType(question.getType());
            if (question.getBodyId() != null) {
                String body = questionBodyService.getQuestionBodyById(question.getBodyId()).getBody();
                questionInfo.setBody(body);
            }
            String[] temps = question.getAnswer().split("\\$\\$");
            if (question.getType().equals("CHOICE")) {
                questionInfo.setOptions(List.of(question.getOptions().split("\\$\\$")));
            }
            String answer = temps[0];
            if (question.getType().equals("FILL_IN_BLANK")) {
                answer = answer.replaceAll("##", ";");
            }
            questionInfo.setAnswer(answer);
            if (temps.length > 1) {
                String explanation = temps[1];
                questionInfo.setExplanation(explanation);
            }
            String knowledgePoint = knowledgePointService.getKnowledgePointNameById(question.getKnowledgePointId());
            questionInfo.setKnowledgePoint(knowledgePoint);
            QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(question.getId(), "small");
            if (questionStatistic.getCompleteCount() != 0) {
                questionInfo.setDifficulty(questionStatistic.getTotalScore() / questionStatistic.getCompleteCount());
            }
            else {
                questionInfo.setDifficulty(-1.0);
            }
            questionInfos.add(questionInfo);
        }
        for (String typeName : typeNames) {
            List<PreAssembledQuestion> questions = preAssembledQuestionService.getPreAssembledQuestionsByType(typeName);
            if (questions == null || questions.isEmpty()) continue;
            Collections.shuffle(questions);
            // 随机抽取一道
            PreAssembledQuestion question = questions.get(0);
            AutoPaperResponse.BigQuestion bigQuestionInfo = new AutoPaperResponse.BigQuestion();
            bigQuestionInfo.setId(question.getId());
            bigQuestionInfo.setBody(question.getQuestionBody());
            List<AutoPaperResponse.SubQuestion> subQuestions = new ArrayList<>();
            for (SubQuestion subQ : question.getSubQuestions()) {
                AutoPaperResponse.SubQuestion subInfo = new AutoPaperResponse.SubQuestion();
                subInfo.setType(subQ.getType());
                subInfo.setContent(subQ.getQuestionContent());
                subInfo.setAnswer(subQ.getQuestionAnswer());
                subInfo.setExplanation(subQ.getQuestionExplanation());
                if (subQ.getType().equals("CHOICE")) {
                    subInfo.setOptions(List.of(subQ.getQuestionOptions().split("\\$\\$")));
                }
                subInfo.setKnowledgePoint(subQ.getKnowledgePoint());
                subQuestions.add(subInfo);
            }
            QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(question.getId(), "big");

            if (questionStatistic.getCompleteCount() != 0) {
                bigQuestionInfo.setDifficulty(questionStatistic.getTotalScore() / questionStatistic.getCompleteCount());
            }
            else {
                bigQuestionInfo.setDifficulty(-1.0);
            }
            bigQuestionInfos.add(bigQuestionInfo);
            bigQuestionInfo.setSubQuestions(subQuestions);
        }
        response.setMessage("success");
        response.setQuestions(questionInfos);
        response.setBigQuestions(bigQuestionInfos);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/teacher/mark-submission")
    public ResponseEntity<Message> markSubmission(@AuthenticationPrincipal BaseUser user, @RequestBody MarkSubmissionRequest request) throws JsonProcessingException{
        Message response = new Message();
        int score = 0;
        AssignmentSubmission assignmentSubmission = null;
        try {
            for(MarkSubmissionRequest.infoData infoData : request.getData()){
                if(infoData.getMarkScore() != null){
                    SubmissionAnswer submissionAnswer = submissionAnswerService.selectById(infoData.getSubmissionAnswerId());
                    if(assignmentSubmission == null){
                        assignmentSubmission = assignmentSubmissionService.selectById(submissionAnswer.getSubmissionId());
                        if(assignmentSubmission.getTotalScore() != null){
                            response.setMessage("不允许重复提交");
                            return ResponseEntity.ok(response);
                        }
                    }
                    submissionAnswer.setScore(infoData.getMarkScore());
                    score += submissionAnswer.getScore();
                    submissionAnswerService.update(submissionAnswer);
                    Long scoreTemp = 100L * submissionAnswer.getScore() / submissionAnswer.getQuestionScore();


                    Question question = new Question();
                    try {
                        question = questionService.getQuestionById(submissionAnswer.getQuestionId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    QuestionStatistic questionStatistic = questionStatisticService.findByIdAndType(question.getId(), "small");
                    questionStatistic.setCompleteCount(questionStatistic.getCompleteCount() + 1);
                    questionStatistic.setTotalScore(questionStatistic.getTotalScore() + scoreTemp);
                    questionStatisticService.update(questionStatistic);
                    if(question.getBodyId() != null){
                        QuestionStatistic bodyQuestionStatistic = questionStatisticService.findByIdAndType(question.getBodyId(), "big");
                        bodyQuestionStatistic.setCompleteCount(bodyQuestionStatistic.getCompleteCount() + 1);
                        bodyQuestionStatistic.setTotalScore(bodyQuestionStatistic.getTotalScore() + scoreTemp);
                        questionStatisticService.update(bodyQuestionStatistic);
                    }


                    AssignmentStatsView assignmentStatsView = assignmentStatsViewService.selectBySubmissionAnswerId(infoData.getSubmissionAnswerId());
                    if(assignmentStatsView.getStatsScore() != null){
                        StatsStudent statsStudent = statsStudentService.selectByStudentIdAndKnowledgePointId(assignmentStatsView.getStudentId(), assignmentStatsView.getKnowledgePointId());
                        statsStudent.setTotalScore(statsStudent.getTotalScore() + 100);
                        statsStudent.setScore(statsStudent.getScore() + scoreTemp);
                        statsStudentService.updateStatsStudent(statsStudent);
                    }
                    else {
                        StatsStudent statsStudent = new StatsStudent();
                        statsStudent.setStudentId(assignmentStatsView.getStudentId());
                        statsStudent.setKnowledgePointId(assignmentStatsView.getKnowledgePointId());
                        statsStudent.setTotalScore(100L);
                        statsStudent.setScore(scoreTemp);
                        statsStudentService.addStatsStudent(statsStudent);
                    }

                }
            }
        }catch (Exception e){
            response.setMessage("error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (assignmentSubmission != null) {
            assignmentSubmission.setTotalScore(score);
            assignmentSubmissionService.update(assignmentSubmission);
        }
        response.setMessage("success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/class/historical-scores")
    public ResponseEntity<HistoricalScoresResponse> getClassHistoricalScores(@RequestParam Long classId){
        HistoricalScoresResponse response = new HistoricalScoresResponse();
        List<HistoricalScoresResponse.infoData> data = new ArrayList<>();
        List<AssignmentScoresView> assignmentScoresViews = assignmentScoresViewService.selectAvgScoresByClassId(classId);
        if(assignmentScoresViews != null){
            assignmentScoresViews.sort(Comparator.comparing(AssignmentScoresView::getAssignmentId));
            Double totalScore = 0.0;
            int count = 0;
            Double subScore = 0.0;
            int subCount = 0;
            Long assignmentId = assignmentScoresViews.get(0).getAssignmentId();
            HistoricalScoresResponse.infoData infoData = new HistoricalScoresResponse.infoData();
            if(assignmentScoresViews.get(0).getScore() != null){
                subScore += assignmentScoresViews.get(0).getScore();
                subCount += 1;
                totalScore += assignmentScoresViews.get(0).getScore();
                count += 1;
            }
            for(int i = 1; i < assignmentScoresViews.size(); i++){
                if(!Objects.equals(assignmentId, assignmentScoresViews.get(i).getAssignmentId())){
                    Assignment assignmentTemp = assignmentService.selectById(assignmentId);
                    if(assignmentTemp.getEndTime() != null){
                        infoData.setEndTime(assignmentTemp.getEndTime().toString());
                    }
                    else{
                        infoData.setEndTime(null);
                    }
                    if(subCount > 0){
                        infoData.setScore(new BigDecimal(subScore).divide(new BigDecimal(subCount), 2, RoundingMode.HALF_UP));
                    }
                    else{
                        infoData.setScore(null);
                    }
                    data.add(infoData);
                    assignmentId = assignmentScoresViews.get(i).getAssignmentId();
                    infoData = new HistoricalScoresResponse.infoData();
                    subScore = 0.0;
                    subCount = 0;
                }
                if(assignmentScoresViews.get(i).getScore() != null){
                    subScore += assignmentScoresViews.get(i).getScore();
                    subCount += 1;
                    totalScore += assignmentScoresViews.get(i).getScore();
                    count += 1;
                }
            }
            Assignment assignmentTemp = assignmentService.selectById(assignmentId);
            if(assignmentTemp.getEndTime() != null){
                infoData.setEndTime(assignmentTemp.getEndTime().toString());
            }
            else{
                infoData.setEndTime(null);
            }
            if(subCount > 0){
                infoData.setScore(new BigDecimal(subScore).divide(new BigDecimal(subCount), 2, RoundingMode.HALF_UP));
            }
            else{
                infoData.setScore(null);
            }
            data.add(infoData);
            if(count > 0){
                response.setAvgScore(new BigDecimal(totalScore).divide(new BigDecimal(count), 2, RoundingMode.HALF_UP));
            }
            else{
                response.setAvgScore(null);
            }
            data.sort(Comparator.comparing(HistoricalScoresResponse.infoData::getEndTime).reversed());
            response.setData(data);
        }
        response.setMessage("success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group/historical-scores")
    public ResponseEntity<HistoricalScoresResponse> getGroupHistoricalScores(@RequestParam Long groupId){
        HistoricalScoresResponse response = new HistoricalScoresResponse();
        List<HistoricalScoresResponse.infoData> data = new ArrayList<>();
        List<AssignmentScoresView> assignmentScoresViews = assignmentScoresViewService.selectAvgScoresByClassId(groupId);
        if(assignmentScoresViews != null){
            assignmentScoresViews.sort(Comparator.comparing(AssignmentScoresView::getAssignmentId));
            Double totalScore = 0.0;
            int count = 0;
            Double subScore = 0.0;
            int subCount = 0;
            Long assignmentId = assignmentScoresViews.get(0).getAssignmentId();
            HistoricalScoresResponse.infoData infoData = new HistoricalScoresResponse.infoData();
            if(assignmentScoresViews.get(0).getScore() != null){
                subScore += assignmentScoresViews.get(0).getScore();
                subCount += 1;
                totalScore += assignmentScoresViews.get(0).getScore();
                count += 1;
            }
            for(int i = 1; i < assignmentScoresViews.size(); i++){
                if(!Objects.equals(assignmentId, assignmentScoresViews.get(i).getAssignmentId())){
                    Assignment assignmentTemp = assignmentService.selectById(assignmentId);
                    if(assignmentTemp.getEndTime() != null){
                        infoData.setEndTime(assignmentTemp.getEndTime().toString());
                    }
                    else{
                        infoData.setEndTime(null);
                    }
                    if(subCount > 0){
                        infoData.setScore(new BigDecimal(subScore).divide(new BigDecimal(subCount), 2, RoundingMode.HALF_UP));
                    }
                    else{
                        infoData.setScore(null);
                    }
                    data.add(infoData);
                    assignmentId = assignmentScoresViews.get(i).getAssignmentId();
                    infoData = new HistoricalScoresResponse.infoData();
                    subScore = 0.0;
                    subCount = 0;
                }
                if(assignmentScoresViews.get(i).getScore() != null){
                    subScore += assignmentScoresViews.get(i).getScore();
                    subCount += 1;
                    totalScore += assignmentScoresViews.get(i).getScore();
                    count += 1;
                }
            }
            Assignment assignmentTemp = assignmentService.selectById(assignmentId);
            if(assignmentTemp.getEndTime() != null){
                infoData.setEndTime(assignmentTemp.getEndTime().toString());
            }
            else{
                infoData.setEndTime(null);
            }
            if(subCount > 0){
                infoData.setScore(new BigDecimal(subScore).divide(new BigDecimal(subCount), 2, RoundingMode.HALF_UP));
            }
            else{
                infoData.setScore(null);
            }
            data.add(infoData);
            if(count > 0){
                response.setAvgScore(new BigDecimal(totalScore).divide(new BigDecimal(count), 2, RoundingMode.HALF_UP));
            }
            else{
                response.setAvgScore(null);
            }
            data.sort(Comparator.comparing(HistoricalScoresResponse.infoData::getEndTime).reversed());
            response.setData(data);
        }
        response.setMessage("success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/class/knowledge-point-status")
    public ResponseEntity<ClassKnowledgePointStatusResponse> getClassKnowledgePointStatus(@RequestParam Long classId){
        ClassKnowledgePointStatusResponse response = new ClassKnowledgePointStatusResponse();
        List<ClassKnowledgePointStatusResponse.infoData> data = new ArrayList<>();
        List<StudentStatsView> studentStatsViews = studentStatsViewService.selectByClassId(classId);
        if(studentStatsViews != null){
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
}

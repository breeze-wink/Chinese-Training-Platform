package com.example.controller;

import com.example.dto.request.teacher.CreateGroupRequest;
import com.example.dto.request.teacher.TeacherCreateClassRequest;
import com.example.dto.request.teacher.UpdateClassRequest;
import com.example.dto.request.teacher.UploadQuestionRequest;
import com.example.dto.response.*;
import com.example.dto.response.student.AvgScoreResponse;
import com.example.dto.response.student.HistoryScoresResponse;
import com.example.dto.response.student.MultidimensionalScoresResponse;
import com.example.dto.response.student.WeaknessScoresResponse;
import com.example.dto.response.teacher.*;
import com.example.model.classes.*;
import com.example.model.course.CourseStandard;
import com.example.model.course.KnowledgePoint;
import com.example.model.question.Question;
import com.example.model.question.QuestionBody;
import com.example.model.submission.AssignmentSubmission;
import com.example.model.user.StatsStudent;
import com.example.service.classes.*;
import com.example.service.classes.impl.*;
import com.example.service.course.CourseStandardService;
import com.example.service.course.KnowledgePointService;
import com.example.service.course.impl.CourseStandardServiceImpl;
import com.example.service.course.impl.KnowledgePointServiceImpl;
import com.example.service.question.QuestionBodyService;
import com.example.service.question.QuestionService;
import com.example.service.question.impl.QuestionBodyServiceImpl;
import com.example.service.question.impl.QuestionServiceImpl;
import com.example.service.submission.AssignmentSubmissionService;
import com.example.service.submission.impl.AssignmentSubmissionServiceImpl;
import com.example.service.user.StatsStudentService;
import com.example.service.user.StudentService;
import com.example.service.user.impl.StatsStudentServiceImpl;
import com.example.service.user.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
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
    private final QuestionService questionService;
    private final QuestionBodyService questionBodyService;
    private final JoinClassService joinClassService;
    private final StatsStudentService statsStudentService;
    private final AssignmentSubmissionService assignmentSubmissionService;
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
                                     AssignmentSubmissionServiceImpl assignmentSubmissionService
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
        this.statsStudentService = statsStudentService;
        this.assignmentSubmissionService = assignmentSubmissionService;
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
            List<KnowledgePoint> knowledgePoints = knowledgePointService.getAllKnowledgePointsOrderByType();

            // 按 type 分组
            Map<String, List<KnowledgePointsResponse.KnowledgePointInfo>> groupedPoints =
                    knowledgePoints.stream()
                            .map(kp -> {
                                KnowledgePointsResponse.KnowledgePointInfo info = new KnowledgePointsResponse.KnowledgePointInfo();
                                info.setName(kp.getName());
                                info.setType(kp.getType());
                                info.setDescription(kp.getDescription());
                                return info;
                            })
                            .collect(Collectors.groupingBy(KnowledgePointsResponse.KnowledgePointInfo::getType));
            response.setMessage("获取成功");
            response.setKnowledgePoints(groupedPoints);

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
            List<KnowledgePoint> knowledgePoints = knowledgePointService.getAllKnowledgePointsOrderByType();

            // 按 type 分组
            Map<String, List<ListKnowledgeResponse.KnowledgePointInfo>> groupedPoints =
                    knowledgePoints.stream()
                            .collect(Collectors.groupingBy(KnowledgePoint::getType))  // 先按 KnowledgePoint 的 type 进行分组
                            .entrySet().stream()  // 获取分组后的 EntrySet
                            .collect(Collectors.toMap(
                                    Map.Entry::getKey,  // 使用 type 作为键
                                    entry -> entry.getValue().stream()
                                            .map(kp -> {
                                                ListKnowledgeResponse.KnowledgePointInfo info = new ListKnowledgeResponse.KnowledgePointInfo();
                                                info.setName(kp.getName());
                                                info.setId(kp.getId());
                                                return info;
                                            })
                                            .collect(Collectors.toList())  // 转换为 List<KnowledgePointInfo>
                            ));

            response.setMessage("获取成功");
            response.setKnowledgePoints(groupedPoints);

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
            if(request.getQuestionType() == null || request.getQuestionType().isEmpty()){
                response.setMessage("题型不能为空");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
           
            questionBody.setBody(request.getBody());
            questionBody.setType(request.getQuestionType());

            List<UploadQuestionRequest.QuestionInfo> questions = request.getQuestions();
            if (questions == null || questions.isEmpty()) {
                response.setMessage("题目数量不能为零");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            else if(questions.size() > 1 && (request.getBody() == null || request.getBody().isEmpty())){
                response.setMessage("组合题题干不能为空");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            for(UploadQuestionRequest.QuestionInfo questionInfo : questions){
                if(questionInfo.getProblem() == null || questionInfo.getProblem().isEmpty()){
                    response.setMessage("题目不能为空");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                else if(questionInfo.getType() == null || questionInfo.getType().isEmpty()){
                    response.setMessage("题目类型不能为空");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                else if(questionInfo.getKnowledgePointId() == null){
                    response.setMessage("知识点不能为空");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                else if(questionInfo.getType().equals("CHOICE") && (questionInfo.getChoices() == null || questionInfo.getChoices().isEmpty())){
                    response.setMessage("选择题选项不能为空");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }
            questionBodyService.createQuestionBody(questionBody);
            for (UploadQuestionRequest.QuestionInfo questionInfo : questions) {
                Question question = getQuestion(id, questionInfo, questionBody);
                questionService.createQuestion(question);
            }
            return ResponseEntity.ok(new Message("上传成功"));
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("上传失败" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private static Question getQuestion(Long id, UploadQuestionRequest.QuestionInfo questionInfo, QuestionBody questionBody) {
        Question question = new Question();
        question.setBodyId(questionBody.getId());
        question.setType(questionInfo.getType());
        question.setContent(questionInfo.getProblem());
        question.setKnowledgePointId(questionInfo.getKnowledgePointId());
        question.setCreatorId(id);

        StringBuilder resAnswer = new StringBuilder();
        if(questionInfo.getAnswer() != null && (!questionInfo.getAnswer().isEmpty())){
            for (int i = 0; i < questionInfo.getAnswer().size(); i ++) {
                if(questionInfo.getAnswer().get(i) == null || questionInfo.getAnswer().get(i).isEmpty()){
                    resAnswer.append(" ");
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



    @GetMapping("{id}/get-question")
    public ResponseEntity<GetQuestionResponse> getQuestion(@PathVariable Long id, @RequestParam Long bodyId) {
        GetQuestionResponse response = new GetQuestionResponse();
        List<GetQuestionResponse.infoData> data = new ArrayList<>();
        QuestionBody questionBody = questionBodyService.getQuestionBodyById(bodyId);
        if(questionBody == null){
            response.setMessage("问题不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<Question> questions = questionService.getQuestionsByQuestionBodyId(bodyId);
        response.setBody(null);
        if(questionBody.getBody() != null && !questionBody.getBody().isEmpty()){
            response.setBody(questionBody.getBody());
        }
        for(Question question : questions){
            GetQuestionResponse.infoData infoData = new GetQuestionResponse.infoData();
            infoData.setContent(question.getContent());
            infoData.setType(question.getType());
            infoData.setOptions(null);
            if(Objects.equals(infoData.getType(), "CHOICE")){
                infoData.setOptions(drawOptions(question.getOptions()));
            }
            String [] temp = question.getAnswer().split("\\$\\$");
            infoData.setAnswer(temp[0]);
            infoData.setAnalysis(temp[1]);
            infoData.setKnowledgePointName(knowledgePointService.getKnowledgePointById(question.getKnowledgePointId()).getName());
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("问题获取成功");
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


    @DeleteMapping("/delete-question/{id}")
    public ResponseEntity<Message> deleteQuestion(@PathVariable Long id) {
        Message response = new Message();
        QuestionBody questionBody = questionBodyService.getQuestionBodyById(id);
        if(questionBody == null){
            response.setMessage("问题不存在");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else{
            int result = questionBodyService.deleteQuestionBody(id);
            if(result == 0){
                response.setMessage("删除失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            else{
                response.setMessage("删除成功");
                return ResponseEntity.ok(response);
            }
        }
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
        Clazz clazz = classService.getClassById(classStudentService.getClassStudentByStudentId(studentId).getClassId());
        if(!Objects.equals(clazz.getCreatorId(), id)){
            response.setMessage("无权限");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<HistoryScoresResponse.infoData> data = new ArrayList<>();
        List<AssignmentSubmission> submissions = assignmentSubmissionService.selectByStudentId(studentId);
        submissions.sort(Comparator.comparing(AssignmentSubmission::getSubmitTime).reversed());
        for(int i = 0; i < submissions.size() && i < 10; i++){
            HistoryScoresResponse.infoData infoData = new HistoryScoresResponse.infoData();
            infoData.setDate(submissions.get(i).getSubmitTime().toString());
            infoData.setScore(null);
            if(submissions.get(i).getTotalScore() != null){
                infoData.setScore(Double.valueOf(String.valueOf(submissions.get(i).getTotalScore())));
            }
            data.add(infoData);
        }
        response.setData(data);
        response.setMessage("历史成绩获取成功");
        return ResponseEntity.ok(response);
    }
}

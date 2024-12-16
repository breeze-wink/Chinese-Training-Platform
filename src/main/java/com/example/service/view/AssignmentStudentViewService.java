package com.example.service.view;

import com.example.model.view.AssignmentStudentView;

import java.util.List;

public interface AssignmentStudentViewService {
    AssignmentStudentView selectByAssignmentIdAndStudentId(Long assignmentId, Long studentId);
    List<AssignmentStudentView> selectByAssignmentId(Long assignmentId);
    List<AssignmentStudentView> selectByStudentId(Long studentId);
    List<AssignmentStudentView> selectByTeacherId(Long teacherId);
}

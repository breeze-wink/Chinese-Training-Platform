package com.example.service.question;

import com.example.model.question.Assignment;

import java.util.List;

public interface AssignmentService {
    Assignment selectById(Long id);
    int createAssignment(Assignment assignment);

    int deleteAssignment(Long id);

    int updateAssignment(Assignment assignment);

    List<Assignment> getAllAssignments();

    boolean checkPaperUsed(Long id);
    List<Assignment> getAssignmentsByTeacherId(Long teacherId);
}

package com.example.service.view;

import com.example.model.view.AssignmentIdStudentIdScore;
import com.example.model.view.AssignmentScoresView;

import java.util.List;

public interface AssignmentScoresViewService {
    List<AssignmentScoresView> selectAvgScoresByClassId(Long classId);
    List<AssignmentIdStudentIdScore> selectScoresByStudentId(Long studentId);
    List<AssignmentScoresView> selectByGroupId(Long groupId);
}

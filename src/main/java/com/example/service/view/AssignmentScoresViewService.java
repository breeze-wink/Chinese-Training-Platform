package com.example.service.view;

import com.example.model.view.AssignmentScoresView;

import java.util.List;

public interface AssignmentScoresViewService {
    List<AssignmentScoresView> selectAvgScoresByClassId(Long classId);
}

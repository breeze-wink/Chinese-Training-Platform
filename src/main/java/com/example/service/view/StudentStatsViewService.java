package com.example.service.view;

import com.example.model.view.StudentStatsView;

import java.util.List;

public interface StudentStatsViewService {
    List<StudentStatsView> selectByClassId(Long classId);
}

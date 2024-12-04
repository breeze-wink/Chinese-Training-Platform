package com.example.service.user;

import com.example.model.user.StatsStudent;

import java.util.List;

public interface StatsStudentService {
    int addStatsStudent(StatsStudent statsStudent);
    int updateStatsStudent(StatsStudent statsStudent);
    List<StatsStudent> getStatsStudentByStudentId(Long studentId);
}

package com.example.service.view.impl;

import com.example.mapper.view.TeacherQuestionStatisticMapper;
import com.example.model.view.TeacherQuestionStatistic;
import com.example.service.view.TeacherQuestionStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherQuestionStatisticServiceImpl implements TeacherQuestionStatisticService {

    private final TeacherQuestionStatisticMapper statisticMapper;

    @Autowired
    public TeacherQuestionStatisticServiceImpl(TeacherQuestionStatisticMapper statisticMapper) {
        this.statisticMapper = statisticMapper;
    }

    @Override
    public List<TeacherQuestionStatistic> getStatisticsByTeacherId(Long teacherId) {
        return statisticMapper.selectByTeacherId(teacherId);
    }

    @Override
    public List<TeacherQuestionStatistic> getAllStatistics() {
        return statisticMapper.selectAll();
    }
}
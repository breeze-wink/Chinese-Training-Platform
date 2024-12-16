package com.example.service.view;

import com.example.model.view.TeacherQuestionStatistic;

import java.util.List;

public interface TeacherQuestionStatisticService {

    /**
     * 根据 teacherId 查询教师的题目统计信息
     *
     * @param teacherId 教师的唯一标识符
     * @return 题目统计信息列表
     */
    List<TeacherQuestionStatistic> getStatisticsByTeacherId(Long teacherId);

    /**
     * 查询所有教师的题目统计信息
     *
     * @return 所有教师的题目统计信息列表
     */
    List<TeacherQuestionStatistic> getAllStatistics();
}
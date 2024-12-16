package com.example.mapper.view;

import com.example.model.view.TeacherQuestionStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeacherQuestionStatisticMapper {

    /**
     * 根据 teacherId 查询教师的题目统计信息
     *
     * @param teacherId 教师的唯一标识符
     * @return 题目统计信息列表
     */
    @Select("SELECT teacherId, uploadTime, questionId, questionType, status, comment, executeTeacher " +
            "FROM teacher_question_statistics " +
            "WHERE teacherId = #{teacherId}")
    List<TeacherQuestionStatistic> selectByTeacherId(Long teacherId);

    /**
     * 查询所有教师的题目统计信息
     *
     * @return 所有教师的题目统计信息列表
     */
    @Select("SELECT teacherId, uploadTime, questionId, questionType, status, comment,executeTeacher " +
            "FROM teacher_question_statistics")
    List<TeacherQuestionStatistic> selectAll();
}
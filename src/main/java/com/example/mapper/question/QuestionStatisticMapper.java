package com.example.mapper.question;

import com.example.dto.mapper.QuestionStatisticDTO;
import com.example.model.question.QuestionStatistic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionStatisticMapper {

    @Insert("INSERT INTO question_statistic (id, type) " +
            "VALUES (#{id}, #{type})")
    void insert(QuestionStatistic questionStatistic);

    @Update("UPDATE question_statistic SET " +
            "uploadTime = #{uploadTime}, " +
            "totalScore = #{totalScore}, " +
            "completeCount = #{completeCount}, " +
            "referencedCount = #{referencedCount} " +
            "WHERE id = #{id} AND type = #{type}")
    void update(QuestionStatistic questionStatistic);

    @Delete("DELETE FROM question_statistic WHERE id = #{id} AND type = #{type}")
    void delete(@Param("id") Long id, @Param("type") String type);

    @Select("SELECT * FROM question_statistic WHERE id = #{id} AND type = #{type}")
    QuestionStatistic findByIdAndType(@Param("id") Long id, @Param("type") String type);

    @Select("SELECT * FROM question_statistic")
    List<QuestionStatistic> findAll();

    void addReferencedCount(@Param("question") QuestionStatisticDTO question);
}
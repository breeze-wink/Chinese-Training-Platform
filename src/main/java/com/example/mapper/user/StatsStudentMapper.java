package com.example.mapper.user;


import com.example.model.user.StatsStudent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StatsStudentMapper {

    @Insert("INSERT INTO stats_student(studentId, knowledgePointId, score, totalScore) VALUES(#{studentId}, #{knowledgePointId}, #{score}, #{totalScore})")
    int insert(StatsStudent statsStudent);

    @Update("UPDATE stats_student SET studentId = #{studentId}, knowledgePointId = #{knowledgePointId}, score = #{score}, totalScore = #{totalScore} WHERE studentId = #{studentId} AND knowledgePointId = #{knowledgePointId}")
    int update(StatsStudent statsStudent);

    @Select("SELECT * FROM stats_student WHERE studentId = #{studentId}")
    List<StatsStudent> selectByStudentId(Long studentId);

    @Select("SELECT * FROM stats_student WHERE studentId = #{studentId} AND knowledgePointId = #{knowledgePointId}")
    StatsStudent selectByStudentIdAndKnowledgePointId(Long studentId, Long knowledgePointId);
}

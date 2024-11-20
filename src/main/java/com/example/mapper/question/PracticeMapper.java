package com.example.mapper.question;

import com.example.model.question.Practice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PracticeMapper {

    @Insert("INSERT INTO practice(studentId, name, practiceTime, totalScore) VALUES(#{studentId}, #{name}, #{practiceTime}, #{totalScore})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Practice record);

    @Delete("DELETE FROM practice WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE practice SET studentId = #{studentId}, name = #{name}, practiceTime = #{practiceTime}, totalScore = #{totalScore} WHERE id = #{id}")
    int update(Practice record);

    @Select("SELECT * FROM practice WHERE id = #{id}")
    Practice selectById(Long id);

    @Select("SELECT * FROM practice")
    List<Practice> selectAll();

    @Select("SELECT * FROM practice WHERE studentId = #{studentId}")
    List<Practice> selectByStudentId(Long studentId);

}

package com.example.mapper.submission;

import com.example.model.submission.PracticeRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PracticeRecordMapper {

    @Insert("INSERT INTO practice_record(studentId, knowledgePointId, practiceTime, totalScore) VALUES(#{studentId}, #{knowledgePointId}, #{practiceTime}, #{totalScore})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PracticeRecord record);

    @Delete("DELETE FROM practice_record WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE practice_record SET studentId = #{studentId}, knowledgePointId = #{knowledgePointId}, practiceTime = #{practiceTime}, totalScore = #{totalScore} WHERE id = #{id}")
    int update(PracticeRecord record);

    @Select("SELECT * FROM practice_record WHERE id = #{id}")
    PracticeRecord selectById(Long id);

    @Select("SELECT * FROM practice_record")
    List<PracticeRecord> selectAll();
}

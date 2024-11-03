package com.example.mapper.question;

import com.example.model.question.Assignment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentMapper {

    @Insert("INSERT INTO assignment(title, description, startTime, endTime, creatorId, paperId) VALUES(#{title}, #{description}, #{startTime}, #{endTime}, #{creatorId}, #{paperId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Assignment assignment);

    @Delete("DELETE FROM assignment WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE assignment SET title = #{title}, description = #{description}, startTime = #{startTime}, endTime = #{endTime}, creatorId = #{creatorId}, paperId = #{paperId} WHERE id = #{id}")
    int update(Assignment assignment);

    @Select("SELECT * FROM assignment WHERE id = #{id}")
    Assignment selectById(Long id);

    @Select("SELECT * FROM assignment")
    List<Assignment> selectAll();
}

package com.example.mapper.question;

import com.example.model.question.Assignment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentMapper {

    @Insert("INSERT INTO assignment(title, description, start_time, end_time, creator_id, class_id) VALUES(#{title}, #{description}, #{startTime}, #{endTime}, #{creatorId}, #{classId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Assignment assignment);

    @Delete("DELETE FROM assignment WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE assignment SET title = #{title}, description = #{description}, start_time = #{startTime}, end_time = #{endTime}, creator_id = #{creatorId}, class_id = #{classId} WHERE id = #{id}")
    int update(Assignment assignment);

    @Select("SELECT * FROM assignment WHERE id = #{id}")
    Assignment selectById(Long id);

    @Select("SELECT * FROM assignment")
    List<Assignment> selectAll();
}

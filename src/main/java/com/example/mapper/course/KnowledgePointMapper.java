package com.example.mapper.course;

import com.example.model.course.KnowledgePoint;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KnowledgePointMapper {

    @Insert("INSERT INTO knowledge_point(name, description, courseStandardId) VALUES(#{name}, #{description}, #{courseStandardId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(KnowledgePoint point);

    @Delete("DELETE FROM knowledge_point WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE knowledge_point SET name = #{name}, description = #{description}, courseStandardId = #{courseStandardId} WHERE id = #{id}")
    int update(KnowledgePoint point);

    @Select("SELECT * FROM knowledge_point WHERE id = #{id}")
    KnowledgePoint selectById(Long id);

    @Select("SELECT * FROM knowledge_point")
    List<KnowledgePoint> selectAll();

}
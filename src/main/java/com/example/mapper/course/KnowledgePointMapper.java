package com.example.mapper.course;

import com.example.model.course.KnowledgePoint;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KnowledgePointMapper {

    @Insert("INSERT INTO knowledge_point(name, description, type) VALUES(#{name}, #{description}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(KnowledgePoint point);

    @Update("UPDATE knowledge_point SET isHidden = true WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE knowledge_point SET name = #{name}, description = #{description}, type = #{type} WHERE id = #{id}")
    int update(KnowledgePoint point);

    @Select("SELECT * FROM knowledge_point WHERE id = #{id}")
    KnowledgePoint selectById(Long id);

    @Select("SELECT * FROM knowledge_point where isHidden = false")
    List<KnowledgePoint> selectAll();

    @Select("SELECT * FROM knowledge_point WHERE knowledge_point.isHidden = false order by type")
    List<KnowledgePoint> selectAllOrderByType();
}
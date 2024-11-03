package com.example.mapper.classes;

import com.example.model.classes.class_group;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassGroupMapper {

    @Insert("INSERT INTO class_group(classId, name, description) " +
            "VALUES(#{classId}, #{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(class_group studentGroup);

    @Delete("DELETE FROM class_group WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE class_group SET classId = #{classId}, name = #{name}, description = #{description} " +
            "WHERE id = #{id}")
    int update(class_group studentGroup);

    @Select("SELECT * FROM class_group WHERE id = #{id}")
    class_group selectById(Long id);

    @Select("SELECT * FROM class_group")
    List<class_group> selectAll();

    @Select("SELECT * FROM class_group WHERE classId = #{classId}")
    List<class_group> selectByClassId(Long classId);
}

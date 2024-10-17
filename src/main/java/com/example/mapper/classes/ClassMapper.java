package com.example.mapper.classes;

import com.example.model.classes.Class;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Insert("INSERT INTO class(name, description, inviteCode, creatorId, school_id) VALUES(#{name}, #{description}, #{inviteCode}, #{creatorId}, #{schoolId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Class clazz);

    @Delete("DELETE FROM class WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE class SET name = #{name}, description = #{description}, inviteCode = #{inviteCode}, creatorId = #{creatorId}, school_id = #{schoolId} WHERE id = #{id}")
    int update(Class clazz);

    @Select("SELECT * FROM class WHERE id = #{id}")
    Class selectById(Long id);

    @Select("SELECT * FROM class")
    List<Class> selectAll();
}

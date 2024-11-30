package com.example.mapper.user;

import com.example.model.user.School;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchoolMapper {

    @Insert("INSERT INTO school(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(School school);

    @Delete("DELETE FROM school WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE school SET name = #{name} WHERE id = #{id}")
    int update(School school);

    @Select("SELECT * FROM school WHERE id = #{id}")
    School selectById(Long id);

    @Select("SELECT * FROM school")
    List<School> selectAll();

    @Select("SELECT * FROM school WHERE name = #{schoolName}")
    School selectByName(String schoolName);
}

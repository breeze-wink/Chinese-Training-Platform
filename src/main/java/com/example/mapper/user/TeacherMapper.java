package com.example.mapper.user;

import com.example.model.user.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface TeacherMapper {

    @Insert("INSERT INTO teacher(name, email, password, phoneNumber, schoolId) VALUES(#{name}, #{email}, #{password}, #{phoneNumber}, #{schoolId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE teacher SET name = #{name}, email = #{email}, password = #{password}, phoneNumber = #{phoneNumber}, schoolId = #{schoolId} WHERE id = #{id}")
    int update(Teacher teacher);

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher selectById(Long id);

    @Select("SELECT * FROM teacher")
    List<Teacher> selectAll();
}
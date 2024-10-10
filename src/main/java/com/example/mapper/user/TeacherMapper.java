package com.example.mapper.user;

import com.example.model.user.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface TeacherMapper {

    @Insert("INSERT INTO teacher(name, email, password, phone_number, school_id) VALUES(#{name}, #{email}, #{password}, #{phoneNumber}, #{schoolId})")
    int insert(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE teacher SET name = #{name}, email = #{email}, password = #{password}, phone_number = #{phoneNumber}, school_id = #{schoolId} WHERE id = #{id}")
    int update(Teacher teacher);

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher selectById(Long id);

    @Select("SELECT * FROM teacher")
    List<Teacher> selectAll();
}
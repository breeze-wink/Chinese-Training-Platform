package com.example.mapper.user;

import com.example.model.user.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface TeacherMapper {

    @Insert("INSERT INTO teacher(name, username, email, password, phoneNumber, schoolId) VALUES(#{name}, #{username}, #{email}, #{password}, #{phoneNumber}, #{schoolId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE teacher SET name = #{name}, username = #{username}, email = #{email}, password = #{password}, phoneNumber = #{phoneNumber}, schoolId = #{schoolId} WHERE id = #{id}")
    int update(Teacher teacher);

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher selectById(Long id);

    @Select("SELECT * FROM teacher WHERE email = #{email}")
    Teacher findByEmail(String email);

    @Select("SELECT * FROM teacher")
    List<Teacher> selectAll();

    @Select("SELECT * FROM teacher where username = #{username}")
    Teacher findByUsername(String username);

    @Select("SELECT * FROM teacher where schoolId = #{schoolId}")
    List<Teacher> selectBySchoolId(Long schoolId);
}
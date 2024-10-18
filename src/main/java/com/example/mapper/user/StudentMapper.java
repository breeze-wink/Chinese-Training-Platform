package com.example.mapper.user;

import com.example.model.user.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Insert("INSERT INTO student(username, email, password, name, grade, schoolId) VALUES(#{username}, #{email}, #{password}, #{name}, #{grade}, #{schoolId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Delete("DELETE FROM student WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE student SET username = #{username}, email = #{email}, password = #{password}, name = #{name}, grade = #{grade}, schoolId = #{schoolId} WHERE id = #{id}")
    int update(Student student);

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student selectById(Long id);

    @Select("SELECT * FROM student")
    List<Student> selectAll();
}

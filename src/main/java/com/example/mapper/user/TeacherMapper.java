package com.example.mapper.user;

import com.example.model.user.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface TeacherMapper {

    @Insert("INSERT INTO teacher(name, username, email, password, phoneNumber, schoolId, permission) VALUES(#{name}, #{username}, #{email}, #{password}, #{phoneNumber}, #{schoolId}, #{permission})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Teacher teacher);

    @Update("UPDATE teacher SET status = 0 WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE teacher SET name = #{name}, username = #{username}, email = #{email}, password = #{password}, phoneNumber = #{phoneNumber}, schoolId = #{schoolId}, permission = #{permission} WHERE id = #{id}")
    int update(Teacher teacher);

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher selectById(Long id);

    @Select("SELECT * FROM teacher WHERE (email = #{email} or username = #{account}) and status = 1")
    List<Teacher> findByAccount(String account);

    @Select("SELECT * FROM teacher WHERE email = #{email} and status = 1")
    Teacher findByEmail(String email);

    @Select("SELECT * FROM teacher where status = 1")
    List<Teacher> selectAll();

    @Select("SELECT * FROM teacher where username = #{username}")
    Teacher findByUsername(String username);

    @Select("SELECT * FROM teacher where schoolId = #{schoolId} and status = 1")
    List<Teacher> selectBySchoolId(Long schoolId);

    @Select("SELECT name FROM teacher where id = #{teacherId}")
    Teacher selectNameById(Long teacherId);

    @Select("SELECT * FROM teacher where email = #{email} and status = 1")
    Teacher emailExist(String email);

    @Select("SELECT * FROM teacher where email = #{email} and status = 1")
    Teacher selectByEmail(String email);


}
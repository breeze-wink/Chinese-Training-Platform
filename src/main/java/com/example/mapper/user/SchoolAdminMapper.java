package com.example.mapper.user;
import com.example.model.user.SchoolAdmin;
import com.example.model.user.SystemAdmin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchoolAdminMapper {

    @Insert("INSERT INTO school_admin(username, password, email, schoolId, name) VALUES(#{username}, #{password}, #{email}, #{schoolId}, #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SchoolAdmin admin);

    @Delete("DELETE FROM school_admin WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE school_admin SET username = #{username}, password = #{password}, email = #{email}, schoolId = #{schoolId}, name = #{name} WHERE id = #{id}")
    int update(SchoolAdmin admin);

    @Select("SELECT * FROM school_admin WHERE id = #{id}")
    SchoolAdmin selectById(Long id);

    @Select("SELECT * FROM school_admin WHERE username = #{account} OR email = #{account}")
    SchoolAdmin findByAccountOrEmail(String account);

    @Select("SELECT * FROM school_admin")
    List<SchoolAdmin> selectAll();

    @Select("SELECT * FROM school_admin WHERE username = #{name}")
    SchoolAdmin selectByUsername(String name);

    @Select("SELECT * FROM school_admin WHERE email = #{email}")
    SchoolAdmin emailExist(String email);

    @Select("SELECT * FROM school_admin WHERE email = #{email}")
    SchoolAdmin selectByEmail(String email);
}
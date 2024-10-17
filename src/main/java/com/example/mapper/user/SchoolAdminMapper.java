package com.example.mapper.user;
import com.example.model.user.SchoolAdmin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchoolAdminMapper {

    @Insert("INSERT INTO school_admin(username, password, email, schoolId) VALUES(#{username}, #{password}, #{email}, #{schoolId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SchoolAdmin admin);

    @Delete("DELETE FROM school_admin WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE school_admin SET username = #{username}, password = #{password}, email = #{email}, schoolId = #{schoolId} WHERE id = #{id}")
    int update(SchoolAdmin admin);

    @Select("SELECT * FROM school_admin WHERE id = #{id}")
    SchoolAdmin selectById(Long id);

    @Select("SELECT * FROM school_admin")
    List<SchoolAdmin> selectAll();
}
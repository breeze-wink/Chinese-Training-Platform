package com.example.mapper.user;
import com.example.model.user.SchoolAdmin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchoolAdminMapper {

    @Insert("INSERT INTO school_admin(username, password, email, school_id) VALUES(#{username}, #{password}, #{email}, #{schoolId})")
    int insert(SchoolAdmin admin);

    @Delete("DELETE FROM school_admin WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE school_admin SET username = #{username}, password = #{password}, email = #{email}, school_id = #{schoolId} WHERE id = #{id}")
    int update(SchoolAdmin admin);

    @Select("SELECT * FROM school_admin WHERE id = #{id}")
    SchoolAdmin selectById(Long id);

    @Select("SELECT * FROM school_admin")
    List<SchoolAdmin> selectAll();
}
package com.example.mapper.user;

import com.example.model.user.SystemAdmin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SystemAdminMapper {

    @Insert("INSERT INTO system_admin(username, password, email) VALUES(#{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SystemAdmin admin);

    @Delete("DELETE FROM system_admin WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE system_admin SET username = #{username}, password = #{password}, email = #{email} WHERE id = #{id}")
    int update(SystemAdmin admin);

    @Select("SELECT * FROM system_admin WHERE id = #{id}")
    SystemAdmin selectById(Long id);

    @Select("SELECT * FROM system_admin")
    List<SystemAdmin> selectAll();
}

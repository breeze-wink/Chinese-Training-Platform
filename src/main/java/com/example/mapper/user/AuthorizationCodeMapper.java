package com.example.mapper.user;

import com.example.model.user.AuthorizationCode;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AuthorizationCodeMapper {

    @Insert("INSERT INTO authorization_code(code, school_id, expiration_date, usage_limit) VALUES(#{code}, #{schoolId}, #{expirationDate}, #{usageLimit})")
    int insert(AuthorizationCode code);

    @Delete("DELETE FROM authorization_code WHERE code = #{code}")
    int delete(String code);

    @Update("UPDATE authorization_code SET school_id = #{schoolId}, expiration_date = #{expirationDate}, usage_limit = #{usageLimit} WHERE code = #{code}")
    int update(AuthorizationCode code);

    @Select("SELECT * FROM authorization_code WHERE code = #{code}")
    AuthorizationCode selectByCode(String code);

    @Select("SELECT * FROM authorization_code")
    List<AuthorizationCode> selectAll();
}

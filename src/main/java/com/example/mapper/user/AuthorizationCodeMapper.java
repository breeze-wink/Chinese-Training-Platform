package com.example.mapper.user;

import com.example.model.user.AuthorizationCode;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AuthorizationCodeMapper {

    @Insert("INSERT INTO authorization_code(code, schoolId, createDate) VALUES(#{code}, #{schoolId}, #{createTime})")
    int insert(AuthorizationCode code);

    @Delete("DELETE FROM authorization_code WHERE schoolId = #{schoolId}")
    int delete(Long schoolId);

    @Update("UPDATE authorization_code SET code = #{code}, createDate = #{createDate} WHERE schoolId = #{schoolId}")
    int update(AuthorizationCode code);

    @Select("SELECT * FROM authorization_code WHERE code = #{code}")
    AuthorizationCode selectByCode(String code);

    @Select("SELECT * FROM authorization_code WHERE schoolId = #{schoolId}")
    AuthorizationCode selectBySchoolId(Long schoolId);

    @Select("SELECT * FROM authorization_code")
    List<AuthorizationCode> selectAll();
}

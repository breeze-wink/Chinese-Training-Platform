package com.example.mapper.classes;

import com.example.model.classes.Clazz;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Insert("INSERT INTO class(name, description, inviteCode, creatorId, schoolId) VALUES(#{name}, #{description}, #{inviteCode}, #{creatorId}, #{schoolId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Clazz clazz);

    @Delete("DELETE FROM class WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE class SET name = #{name}, description = #{description}, inviteCode = #{inviteCode}, creatorId = #{creatorId}, schoolId = #{schoolId} WHERE id = #{id}")
    int update(Clazz clazz);

    @Select("SELECT * FROM class WHERE id = #{id}")
    Clazz selectById(Long id);

    @Select("SELECT * FROM class")
    List<Clazz> selectAll();

    @Select("SELECT * FROM class WHERE EXISTS(SELECT * FROM class WHERE inviteCode = #{inviteCode})")
    Clazz inviteCodeCheck(String inviteCode);

    @Select("SELECT id FROM class WHERE inviteCode = #{inviteCode}")
    Long selectIdByInviteCode(String inviteCode);

    @Select("SELECT * FROM class WHERE creatorId = #{creatorId}")
    List<Clazz> selectByCreatorId(Long teacherId);

    @Select("SELECT * FROM class WHERE schoolId = #{schoolId}")
    List<Clazz> selectBySchoolId(Long schoolId);

    @Delete("DELETE FROM class WHERE creatorId = #{id}")
    void deleteByCreatorId(Long id);
}

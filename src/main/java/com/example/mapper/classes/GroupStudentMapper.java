package com.example.mapper.classes;

import com.example.model.classes.GroupStudent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupStudentMapper {

    @Insert("INSERT INTO group_student(groupId, studentId) VALUES(#{groupId}, #{studentId})")
    int insert(GroupStudent groupStudent);

    @Delete("DELETE FROM group_student WHERE groupId = #{groupId} AND studentId = #{studentId}")
    int delete(@Param("groupId") Long groupId, @Param("studentId") Long studentId);

    @Select("SELECT * FROM group_student WHERE groupId = #{groupId} AND studentId = #{studentId}")
    GroupStudent selectByIds(@Param("groupId") Long groupId, @Param("studentId") Long studentId);

    @Select("SELECT * FROM group_student")
    List<GroupStudent> selectAll();

    @Select("SELECT * FROM group_student WHERE groupId = #{groupId}")
    List<GroupStudent> selectByGroupId(Long groupId);

    @Select("SELECT * FROM group_student WHERE studentId = #{studentId}")
    List<GroupStudent> selectByStudentId(Long studentId);

    @Delete("DELETE FROM group_student WHERE groupId = #{groupId}")
    int removeGroup(Long groupId);
}

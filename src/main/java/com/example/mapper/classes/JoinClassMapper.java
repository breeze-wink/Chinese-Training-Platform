package com.example.mapper.classes;


import com.example.model.classes.JoinClass;
import org.apache.ibatis.annotations.*;

@Mapper
public interface JoinClassMapper {

    @Insert("INSERT INTO join_class(classId, studentId) VALUES(#{classId}, #{studentId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(JoinClass joinClass);

    @Delete("DELETE FROM join_class WHERE studentId = #{studentId}")
    int deleteByStudentId(Long studentId);

    @Delete("DELETE FROM join_class WHERE classId = #{classId}")
    int deleteByClassId(Long classId);

    @Select("SELECT * FROM join_class WHERE studentId = #{studentId} AND classId = #{classId}")
    JoinClass selectJoinClassByStudentIdAndClassId(Long studentId, Long classId);
}

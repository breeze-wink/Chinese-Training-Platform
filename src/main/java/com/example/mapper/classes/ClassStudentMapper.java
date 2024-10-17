package com.example.mapper.classes;

import com.example.model.classes.ClassStudent;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassStudentMapper {

    @Insert("INSERT INTO class_student(classId, studentId, joinDate) VALUES(#{classId}, #{studentId}, #{joinDate})")
    int insert(ClassStudent classStudent);

    @Delete("DELETE FROM class_student WHERE classId = #{classId} AND studentId = #{studentId}")
    int delete(@Param("classId") Long classId, @Param("studentId") Long studentId);

    @Select("SELECT * FROM class_student WHERE classId = #{classId} AND studentId = #{studentId}")
    ClassStudent select(@Param("classId") Long classId, @Param("studentId") Long studentId);

    @Select("SELECT * FROM class_student WHERE classId = #{classId}")
    List<ClassStudent> selectByClassId(Long classId);

    @Select("SELECT * FROM class_student WHERE studentId = #{studentId}")
    List<ClassStudent> selectByStudentId(Long studentId);

    @Delete("DELETE FROM class_student WHERE class_id = #{classID}")
    int removeClass(Long classId);

    @Select("SELECT id FROM class WHERE invite_code = #{inviteCode}")
    long selectIdByInviteCode(String inviteCode);
}

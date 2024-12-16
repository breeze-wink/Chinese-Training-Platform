package com.example.mapper.view;

import com.example.model.view.AssignmentStudentView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssignmentStudentViewMapper {
    @Select("SELECT DISTINCT * FROM assignment_student_view WHERE assignmentId = #{assignmentId} AND studentId = #{studentId}")
    AssignmentStudentView selectByAssignmentIdAndStudentId(Long assignmentId, Long studentId);

    @Select("SELECT DISTINCT * FROM assignment_student_view WHERE assignmentId = #{assignmentId}")
    List<AssignmentStudentView> selectByAssignmentId(Long assignmentId);

    @Select("SELECT DISTINCT * FROM assignment_student_view WHERE studentId = #{studentId}")
    List<AssignmentStudentView> selectByStudentId(Long studentId);

    @Select("SELECT DISTINCT * FROM assignment_student_view WHERE teacherId = #{teacherId}")
    List<AssignmentStudentView> selectByTeacherId(Long teacherId);
}

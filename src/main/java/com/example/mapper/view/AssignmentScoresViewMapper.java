package com.example.mapper.view;

import com.example.model.view.AssignmentIdStudentIdScore;
import com.example.model.view.AssignmentScoresView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssignmentScoresViewMapper {

    @Select("SELECT * FROM assignment_scores_view WHERE classId = #{classId}")
    List<AssignmentScoresView> selectAvgScoresByClassId(Long classId);

    @Select("SELECT DISTINCT assignmentId,studentId,score FROM assignment_scores_view WHERE studentId = #{studentId}")
    List<AssignmentIdStudentIdScore> selectScoresByStudentId(Long studentId);

    @Select("SELECT * FROM assignment_scores_view WHERE groupId = #{groupId}")
    List<AssignmentScoresView> selectByGroupId(Long groupId);
}

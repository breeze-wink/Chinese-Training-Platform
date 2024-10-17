package com.example.mapper.question;

import com.example.model.question.AssignmentQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentQuestionMapper {

    @Insert("INSERT INTO assignment_question(assignmentId, questionId, sequence) VALUES(#{assignmentId}, #{questionId}, #{sequence})")
    int insert(AssignmentQuestion aq);

    @Delete("DELETE FROM assignment_question WHERE assignmentId = #{assignmentId} AND questionId = #{questionId}")
    int delete(@Param("assignmentId") Long assignmentId, @Param("questionId") Long questionId);

    @Select("SELECT * FROM assignment_question WHERE assignmentId = #{assignmentId}")
    List<AssignmentQuestion> selectByAssignmentId(Long assignmentId);

    @Select("SELECT * FROM assignment_question WHERE questionId = #{questionId}")
    List<AssignmentQuestion> selectByQuestionId(Long questionId);
}

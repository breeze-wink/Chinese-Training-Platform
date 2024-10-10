package com.example.mapper.question;

import com.example.model.question.AssignmentQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentQuestionMapper {

    @Insert("INSERT INTO assignment_question(assignment_id, question_id, sequence) VALUES(#{assignmentId}, #{questionId}, #{sequence})")
    int insert(AssignmentQuestion aq);

    @Delete("DELETE FROM assignment_question WHERE assignment_id = #{assignmentId} AND question_id = #{questionId}")
    int delete(@Param("assignmentId") Long assignmentId, @Param("questionId") Long questionId);

    @Select("SELECT * FROM assignment_question WHERE assignment_id = #{assignmentId}")
    List<AssignmentQuestion> selectByAssignmentId(Long assignmentId);

    @Select("SELECT * FROM assignment_question WHERE question_id = #{questionId}")
    List<AssignmentQuestion> selectByQuestionId(Long questionId);
}

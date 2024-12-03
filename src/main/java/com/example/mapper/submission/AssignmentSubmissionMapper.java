package com.example.mapper.submission;

import com.example.model.submission.AssignmentSubmission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentSubmissionMapper {

    @Insert("INSERT INTO assignment_submission(assignmentId, studentId, submitTime, totalScore, graded) VALUES(#{assignmentId}, #{studentId}, #{submitTime}, #{totalScore}, #{graded})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AssignmentSubmission submission);

    @Delete("DELETE FROM assignment_submission WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE assignment_submission SET assignmentId = #{assignmentId}, studentId = #{studentId}, submitTime = #{submitTime}, totalScore = #{totalScore}, graded = #{graded} WHERE id = #{id}")
    int update(AssignmentSubmission submission);

    @Select("SELECT * FROM assignment_submission WHERE id = #{id}")
    AssignmentSubmission selectById(Long id);

    @Select("SELECT * FROM assignment_submission")
    List<AssignmentSubmission> selectAll();

    @Select("SELECT * FROM assignment_submission WHERE studentId = #{studentId}")
    List<AssignmentSubmission> selectByStudentId(Long studentId);
}

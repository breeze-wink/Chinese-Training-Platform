package com.example.mapper.submission;

import com.example.model.submission.AssignmentSubmission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentSubmissionMapper {

    @Insert("INSERT INTO assignment_submission(assignment_id, student_id, submit_time, total_score, graded) VALUES(#{assignmentId}, #{studentId}, #{submitTime}, #{totalScore}, #{graded})")
    int insert(AssignmentSubmission submission);

    @Delete("DELETE FROM assignment_submission WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE assignment_submission SET assignment_id = #{assignmentId}, student_id = #{studentId}, submit_time = #{submitTime}, total_score = #{totalScore}, graded = #{graded} WHERE id = #{id}")
    int update(AssignmentSubmission submission);

    @Select("SELECT * FROM assignment_submission WHERE id = #{id}")
    AssignmentSubmission selectById(Long id);

    @Select("SELECT * FROM assignment_submission")
    List<AssignmentSubmission> selectAll();
}

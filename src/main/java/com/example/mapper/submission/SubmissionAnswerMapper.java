package com.example.mapper.submission;

import com.example.model.submission.SubmissionAnswer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubmissionAnswerMapper {

    @Insert("INSERT INTO submission_answer(submission_id, question_id, answer_content, score, feedback) VALUES(#{submissionId}, #{questionId}, #{answerContent}, #{score}, #{feedback})")
    int insert(SubmissionAnswer answer);

    @Delete("DELETE FROM submission_answer WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE submission_answer SET submission_id = #{submissionId}, question_id = #{questionId}, answer_content = #{answerContent}, score = #{score}, feedback = #{feedback} WHERE id = #{id}")
    int update(SubmissionAnswer answer);

    @Select("SELECT * FROM submission_answer WHERE id = #{id}")
    SubmissionAnswer selectById(Long id);

    @Select("SELECT * FROM submission_answer")
    List<SubmissionAnswer> selectAll();
}

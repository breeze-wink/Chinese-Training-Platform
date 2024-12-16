package com.example.mapper.submission;

import com.example.model.submission.SubmissionAnswer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubmissionAnswerMapper {

    @Insert("INSERT INTO submission_answer(submissionId, questionId, sequence, answerContent, questionScore, score, feedback) VALUES(#{submissionId}, #{questionId}, #{sequence}, #{answerContent}, #{questionScore}, #{score}, #{feedback})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SubmissionAnswer answer);

    @Delete("DELETE FROM submission_answer WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE submission_answer SET submissionId = #{submissionId}, questionId = #{questionId}, sequence = #{sequence}, answerContent = #{answerContent}, questionScore = #{questionScore}, score = #{score}, feedback = #{feedback} WHERE id = #{id}")
    int update(SubmissionAnswer answer);

    @Select("SELECT * FROM submission_answer WHERE id = #{id}")
    SubmissionAnswer selectById(Long id);

    @Select("SELECT * FROM submission_answer")
    List<SubmissionAnswer> selectAll();

    @Select("SELECT * FROM submission_answer WHERE submissionId = #{submissionId}")
    List<SubmissionAnswer> selectBySubmissionId(Long submissionId);
}

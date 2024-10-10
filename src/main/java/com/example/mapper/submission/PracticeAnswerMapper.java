package com.example.mapper.submission;

import com.example.model.submission.PracticeAnswer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PracticeAnswerMapper {

    @Insert("INSERT INTO practice_answer(practice_record_id, question_id, answer_content, score, feedback) VALUES(#{practiceRecordId}, #{questionId}, #{answerContent}, #{score}, #{feedback})")
    int insert(PracticeAnswer answer);

    @Delete("DELETE FROM practice_answer WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE practice_answer SET practice_record_id = #{practiceRecordId}, question_id = #{questionId}, answer_content = #{answerContent}, score = #{score}, feedback = #{feedback} WHERE id = #{id}")
    int update(PracticeAnswer answer);

    @Select("SELECT * FROM practice_answer WHERE id = #{id}")
    PracticeAnswer selectById(Long id);

    @Select("SELECT * FROM practice_answer")
    List<PracticeAnswer> selectAll();
}

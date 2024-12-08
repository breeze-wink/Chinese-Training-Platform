package com.example.mapper.question;

import com.example.model.question.PracticeQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PracticeQuestionMapper {

    @Insert("INSERT INTO practice_question (practiceId, questionId, sequence) VALUES (#{practiceId}, #{questionId}, #{sequence})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPracticeQuestion(PracticeQuestion practiceQuestion);

    @Select("SELECT * FROM practice_question WHERE id = #{id}")
    PracticeQuestion selectPracticeQuestionById(Long id);

    @Select("SELECT * FROM practice_question WHERE practiceId = #{practiceId}")
    List<PracticeQuestion> selectPracticeQuestionsByPracticeId(Long practiceId);

    @Update("UPDATE practice_question SET practiceId = #{practiceId}, questionId = #{questionId}, sequence = #{sequence} WHERE id = #{id}")
    int updatePracticeQuestion(PracticeQuestion practiceQuestion);

    @Delete("DELETE FROM practice_question WHERE id = #{id}")
    int deletePracticeQuestion(Long id);

    @Delete("DELETE FROM practice_question WHERE practiceId = #{practiceId}")
    int deletePracticeQuestionByPracticeId(Long practiceId);

    void insertPracticeQuestions(@Param("list") List<PracticeQuestion> practiceQuestions);
}

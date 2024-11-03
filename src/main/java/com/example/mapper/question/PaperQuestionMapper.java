package com.example.mapper.question;

import com.example.model.question.PaperQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaperQuestionMapper {

    @Insert("INSERT INTO paper_question(paperId, questionId, sequence) VALUES(#{paperId}, #{questionId}, #{sequence})")
    int insert(PaperQuestion pq);

    @Delete("DELETE FROM paper_question WHERE paperId = #{assignmentId} AND questionId = #{questionId}")
    int delete(@Param("paperId") Long assignmentId, @Param("questionId") Long questionId);

    @Select("SELECT * FROM paper_question WHERE paperId = #{assignmentId}")
    List<PaperQuestion> selectByPaperId(Long paperId);

    @Select("SELECT * FROM paper_question WHERE questionId = #{questionId}")
    List<PaperQuestion> selectByQuestionId(Long questionId);
}

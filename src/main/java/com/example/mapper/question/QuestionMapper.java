package com.example.mapper.question;

import com.example.model.question.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(content, type, options, answer, knowledgePointId, bodyId) VALUES(#{content}, #{type}, #{options}, #{answer}, #{knowledgePointId}, #{bodyId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Question question);

    @Update("UPDATE question SET status = 2 WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE question SET status = 3 WHERE id = #{id}")
    void deny(Long id);
    @Delete("DELETE FROM question WHERE id = #{id}")
    int reallyDelete(Long id);

    @Update("UPDATE question SET status = 1 WHERE id = #{id}")
    void access(Long id);

    @Update("UPDATE question SET content = #{content}, options = #{options}, answer = #{answer} WHERE id = #{id}")
    int update(Question question);

    @Select("SELECT * FROM question WHERE id = #{id}")
    Question selectById(Long id);

    @Select("SELECT * FROM question")
    List<Question> selectAll();

    @Select("SELECT * FROM question WHERE knowledgePointId = #{knowledgePointId} and status = 1")
    List<Question> getQuestionsByKnowledgePointId(Long knowledgePointId);

    @Select("SELECT * FROM question WHERE bodyId = #{questionBodyId}")
    List<Question> getQuestionsByQuestionBodyId(Long questionBodyId);

    @Select("SELECT status FROM question WHERE id = #{questionId}")
    Integer getStatus(Long questionId);

//    List<Question> getQuestionsByKnowledgePointIds(List<Long> knowledgePointIds);
}

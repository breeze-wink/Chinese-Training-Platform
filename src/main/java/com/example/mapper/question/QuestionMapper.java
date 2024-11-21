package com.example.mapper.question;

import com.example.model.question.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(content, type, options, answer, knowledgePointId, creatorId) VALUES(#{content}, #{type}, #{options}, #{answer}, #{knowledgePointId}, #{creatorId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Question question);

    @Delete("DELETE FROM question WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE question SET content = #{content}, type = #{type}, options = #{options}, answer = #{answer}, knowledgePointId = #{knowledgePointId}, creatorId = #{creatorId} WHERE id = #{id}")
    int update(Question question);

    @Select("SELECT * FROM question WHERE id = #{id}")
    Question selectById(Long id);

    @Select("SELECT * FROM question")
    List<Question> selectAll();

    @Select("SELECT * FROM question WHERE knowledgePointId = #{knowledgePointId}")
    List<Question> getQuestionsByKnowledgePointId(Long knowledgePointId);
}

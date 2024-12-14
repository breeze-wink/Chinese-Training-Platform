package com.example.mapper.question;

import com.example.model.question.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(content, type, options, answer, knowledgePointId, creatorId, bodyId) VALUES(#{content}, #{type}, #{options}, #{answer}, #{knowledgePointId}, #{creatorId}, #{bodyId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Question question);

    @Update("UPDATE question SET status = 2 WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE question SET status = 1 WHERE id = #{id}")
    void access(Long id);

    @Update("UPDATE question SET content = #{content}, type = #{type}, options = #{options}, answer = #{answer}, knowledgePointId = #{knowledgePointId}, creatorId = #{creatorId}, bodyId = #{bodyId} WHERE id = #{id}")
    int update(Question question);

    @Select("SELECT * FROM question WHERE id = #{id}")
    Question selectById(Long id);

    @Select("SELECT * FROM question")
    List<Question> selectAll();

    @Select("SELECT * FROM question WHERE knowledgePointId = #{knowledgePointId} and status = 2")
    List<Question> getQuestionsByKnowledgePointId(Long knowledgePointId);

    @Select("SELECT * FROM question WHERE bodyId = #{questionBodyId} and status = 2")
    List<Question> getQuestionsByQuestionBodyId(Long questionBodyId);

//    List<Question> getQuestionsByKnowledgePointIds(List<Long> knowledgePointIds);
}

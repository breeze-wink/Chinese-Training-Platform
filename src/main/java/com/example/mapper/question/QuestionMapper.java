package com.example.mapper.question;

import com.example.model.question.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question(content, type, options, answer, knowledge_point_id, creator_id) VALUES(#{content}, #{type}, #{options}, #{answer}, #{knowledgePointId}, #{creatorId})")
    int insert(Question question);

    @Delete("DELETE FROM question WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE question SET content = #{content}, type = #{type}, options = #{options}, answer = #{answer}, knowledge_point_id = #{knowledgePointId}, creator_id = #{creatorId} WHERE id = #{id}")
    int update(Question question);

    @Select("SELECT * FROM question WHERE id = #{id}")
    Question selectById(Long id);

    @Select("SELECT * FROM question")
    List<Question> selectAll();
}

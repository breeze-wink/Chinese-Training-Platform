package com.example.mapper.question;

import com.example.model.question.QuestionBody;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionBodyMapper {

    // 插入一条记录
    @Insert("INSERT INTO question_body (body, type) VALUES (#{body}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(QuestionBody questionBody);

    // 根据ID查询记录
    @Select("SELECT * FROM question_body WHERE id = #{id}")
    QuestionBody selectById(Long id);

    // 获取所有记录
    @Select("SELECT * FROM question_body")
    List<QuestionBody> selectAll();

    @Update("UPDATE question_body SET status = 1 WHERE id = #{id}")
    void access(Long id);

    @Update("UPDATE question_body SET status = 3 WHERE id = #{id}")
    void deny(Long id);

    // 更新记录
    @Update("UPDATE question_body SET body = #{body}, type = #{type}, status = #{status} WHERE id = #{id}")
    int update(QuestionBody questionBody);

    // 删除记录
    @Update("Update question_body SET status = 2 WHERE id = #{id}")
    int delete(Long id);

    @Delete("DELETE FROM question_body WHERE id = #{id}")
    int reallyDelete(Long id);

    @Select("SELECT * FROM question_body WHERE type = #{type} and status=1")
    List<QuestionBody> getQuestionBodiesByType(String type);

    @Select("SELECT type FROM question_body where status=1")
    List<String> getAllTypes();

    @Select("SELECT status FROM question_body WHERE id = #{questionId}")
    int getStatus(Long questionId);
}
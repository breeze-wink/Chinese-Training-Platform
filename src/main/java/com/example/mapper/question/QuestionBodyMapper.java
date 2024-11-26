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

    // 更新记录
    @Update("UPDATE question_body SET body = #{body}, type = #{type} WHERE id = #{id}")
    int update(QuestionBody questionBody);

    // 删除记录
    @Delete("DELETE FROM question_body WHERE id = #{id}")
    int delete(Long id);
}
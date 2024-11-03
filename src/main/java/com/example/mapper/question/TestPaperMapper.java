package com.example.mapper.question;

import com.example.model.question.TestPaper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TestPaperMapper {

    @Insert("INSERT INTO test_paper(name, description, creatorId, createTime) " +
            "VALUES(#{name}, #{description}, #{creatorId}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TestPaper testPaper);

    @Delete("DELETE FROM test_paper WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE test_paper SET name = #{name}, description = #{description}, " +
            "creatorId = #{creatorId}, createTime = #{createTime} WHERE id = #{id}")
    int update(TestPaper testPaper);

    @Select("SELECT * FROM test_paper WHERE id = #{id}")
    TestPaper selectById(Long id);

    @Select("SELECT * FROM test_paper")
    List<TestPaper> selectAll();

    @Select("SELECT * FROM test_paper WHERE creatorId = #{creatorId}")
    List<TestPaper> selectByCreatorId(Long creatorId);
}

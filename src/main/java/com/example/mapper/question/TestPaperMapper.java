package com.example.mapper.question;

import com.example.model.question.TestPaper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TestPaperMapper {

    @Insert("INSERT INTO test_paper(name, creatorId, difficulty, totalScore) " +
            "VALUES(#{name}, #{creatorId},#{difficulty}, #{totalScore})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TestPaper testPaper);

    @Update("UPDATE test_paper SET isHidden=true WHERE id = #{id}")
    int delete(Long id);

    @Delete("DELETE FROM test_paper WHERE id = #{id}")
    int realDelete(Long id);

    @Update("UPDATE test_paper SET name = #{name}, difficulty = #{difficulty}," +
            "creatorId = #{creatorId}, createTime = #{createTime}, totalScore = #{totalScore}," +
            "isHidden=#{isHidden} WHERE id = #{id}")
    int update(TestPaper testPaper);

    @Select("SELECT * FROM test_paper WHERE id = #{id}")
    TestPaper selectById(Long id);

    @Select("SELECT * FROM test_paper where isHidden=false")
    List<TestPaper> selectAll();

    @Select("SELECT * FROM test_paper WHERE creatorId = #{creatorId} and isHidden=false")
    List<TestPaper> selectByCreatorId(Long creatorId);

    @Delete("DELETE FROM test_paper WHERE creatorId = #{id}")
    void deleteByCreatorId(Long id);
}

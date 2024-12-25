package com.example.mapper.essay;

import com.example.model.essay.Essay;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EssayMapper {

    @Insert("INSERT INTO essay(title, content, submitDate, teacherId) VALUES(#{title}, #{content}, #{submitDate}, #{teacherId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertEssay(Essay essay);

    @Select("SELECT * FROM essay WHERE id = #{id}")
    Essay getEssayById(Long id);

    @Update("UPDATE essay SET title = #{title}, content = #{content}, submitDate = #{submitDate}, teacherId = #{teacherId} WHERE id = #{id}")
    int updateEssay(Essay essay);

    @Delete("DELETE FROM essay WHERE id = #{id}")
    int deleteEssay(Long id);

    @Select("SELECT * FROM essay")
    List<Essay> getAllEssays();
}

package com.example.mapper.course;

import com.example.model.course.CourseStandard;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseStandardMapper {

    @Insert("INSERT INTO course_standard(title, description) VALUES(#{title}, #{description})")
    int insert(CourseStandard standard);

    @Delete("DELETE FROM course_standard WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE course_standard SET title = #{title}, description = #{description} WHERE id = #{id}")
    int update(CourseStandard standard);

    @Select("SELECT * FROM course_standard WHERE id = #{id}")
    CourseStandard selectById(Long id);

    @Select("SELECT * FROM course_standard")
    List<CourseStandard> selectAll();
}

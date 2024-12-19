package com.example.mapper.view;

import com.example.model.view.StudentStatsView;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentStatsViewMapper {

    @Select("select * from student_stats_view where classId = #{classId}")
    List<StudentStatsView> selectByClassId(Long classId);
}

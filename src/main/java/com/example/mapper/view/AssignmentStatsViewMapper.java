package com.example.mapper.view;

import com.example.model.view.AssignmentStatsView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AssignmentStatsViewMapper {

    @Select("SELECT DISTINCT * FROM assignment_stats_view WHERE submissionAnswerId = #{submissionAnswerId}")
    AssignmentStatsView selectBySubmissionAnswerId(Long submissionAnswerId);
}

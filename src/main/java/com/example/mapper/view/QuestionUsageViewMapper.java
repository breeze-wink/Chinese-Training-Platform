package com.example.mapper.view;

import com.example.model.view.QuestionUsageView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface QuestionUsageViewMapper {

    /**
     * 查询所有问题使用情况
     *
     * @return List<QuestionUsageView>
     */
    @Select("SELECT questionId, type, used FROM question_usage_view")
    List<QuestionUsageView> findAll();

    /**
     * 根据 questionId 查询单个问题使用情况
     *
     * @param questionId 问题ID
     * @return QuestionUsageView
     */
    @Select("SELECT questionId, type, used FROM question_usage_view " +
            "WHERE questionId = #{questionId} and type = #{type}")
    QuestionUsageView findByIdAndType(Long questionId, String type);
}
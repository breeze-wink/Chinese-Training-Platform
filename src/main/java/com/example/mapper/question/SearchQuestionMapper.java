package com.example.mapper.question;

import com.example.dto.mapper.BigQuestionResult;
import com.example.dto.mapper.QuestionResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchQuestionMapper {

    // 查询大题
    List<BigQuestionResult> searchBigQuestions(
            @Param("knowledgeType") String knowledgeType,
            @Param("difficulty") String difficulty,
            @Param("mode") String mode,
            @Param("sortOrder") String sortOrder,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize,
            @Param("search") String search
    );

    // 统计大题总数
    int countBigQuestions(

            @Param("knowledgeType") String knowledgeType,
            @Param("difficulty") String difficulty,
            @Param("search") String search
    );

    // 查询小题
    List<QuestionResult> searchSmallQuestions(
            @Param("type") String type,
            @Param("knowledgeId") Long knowledgeId,
            @Param("difficulty") String difficulty,
            @Param("mode") String mode,
            @Param("sortOrder") String sortOrder,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize,
            @Param("search") String search
    );

    // 统计小题总数
    long countSmallQuestions(
            @Param("type") String type,
            @Param("knowledgeId") Long knowledgeId,
            @Param("difficulty") String difficulty,
            @Param("search") String search
    );
}

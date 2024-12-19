package com.example.mapper.question;

import com.example.model.question.PaperQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperQuestionMapper {

    int insert(PaperQuestion pq);

    int delete(@Param("paperId") Long paperId, @Param("questionId") Long questionId);

    List<PaperQuestion> selectByPaperId(@Param("paperId") Long paperId);

    List<PaperQuestion> selectByQuestionId(@Param("questionId") Long questionId);

    int batchInsert(@Param("list") List<PaperQuestion> pqList);

    List<PaperQuestion> batchSelectByPaperId(@Param("list") List<Long> paperIds);
}

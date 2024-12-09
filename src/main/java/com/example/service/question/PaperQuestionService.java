package com.example.service.question;

import com.example.model.question.PaperQuestion;

import java.util.List;

public interface PaperQuestionService {

    int insert(PaperQuestion pq);

    int delete(Long paperId, Long questionId);

    List<PaperQuestion> selectByPaperId(Long paperId);

    List<PaperQuestion> selectByQuestionId(Long questionId);

    // 批量插入
    int batchInsert(List<PaperQuestion> pqList);

    // 批量获取
    List<PaperQuestion> batchSelectByPaperId(List<Long> paperIds);
}

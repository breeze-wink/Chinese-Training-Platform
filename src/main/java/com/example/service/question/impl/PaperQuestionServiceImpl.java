package com.example.service.question.impl;

import com.example.mapper.question.PaperQuestionMapper;
import com.example.model.question.PaperQuestion;
import com.example.service.question.PaperQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperQuestionServiceImpl implements PaperQuestionService {

    @Autowired
    private PaperQuestionMapper paperQuestionMapper;

    @Override
    public int insert(PaperQuestion pq) {
        return paperQuestionMapper.insert(pq);
    }

    @Override
    public int delete(Long paperId, Long questionId) {
        return paperQuestionMapper.delete(paperId, questionId);
    }

    @Override
    public List<PaperQuestion> selectByPaperId(Long paperId) {
        return paperQuestionMapper.selectByPaperId(paperId);
    }

    @Override
    public List<PaperQuestion> selectByQuestionId(Long questionId) {
        return paperQuestionMapper.selectByQuestionId(questionId);
    }

    @Override
    public int batchInsert(List<PaperQuestion> pqList) {
        if (pqList == null || pqList.isEmpty()) {
            return 0;
        }
        // 这里可以考虑使用 MyBatis 提供的批量插入方法
        // 你可以通过 MyBatis 的 `<foreach>` 标签实现批量插入
        return paperQuestionMapper.batchInsert(pqList);
    }

    @Override
    public List<PaperQuestion> batchSelectByPaperId(List<Long> paperIds) {
        if (paperIds == null || paperIds.isEmpty()) {
            return null;
        }
        return paperQuestionMapper.batchSelectByPaperId(paperIds);
    }
}

package com.example.service.question;

import com.example.model.question.QuestionBody;

import java.util.List;

public interface QuestionBodyService {

    // 插入一条题目记录
    int createQuestionBody(QuestionBody questionBody);

    // 根据ID查询题目
    QuestionBody getQuestionBodyById(Long id);

    // 获取所有题目记录
    List<QuestionBody> getAllQuestionBodies();

    // 更新题目记录
    int updateQuestionBody(QuestionBody questionBody);

    // 删除题目记录
    int deleteQuestionBody(Long id);
    List<QuestionBody> getQuestionBodiesByType(String type);
}
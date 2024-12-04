package com.example.service.question;

import com.example.dto.redis.PreassembledPracticeQuestion;
import com.example.model.question.Question;
import com.example.model.question.QuestionBody;

import java.util.ArrayList;
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

    void syncToRedis(QuestionBody questionBody);

    void deleteFromRedis(Long id);

    List<QuestionBody> getQuestionBodiesByIds(ArrayList<Long> longs);

    List<PreassembledPracticeQuestion> getPreassembledQuestionsByTypes(List<String> types);
    void flushPreassembledQuestions();
}
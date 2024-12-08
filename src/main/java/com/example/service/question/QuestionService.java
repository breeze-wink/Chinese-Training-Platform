package com.example.service.question;

import com.example.model.question.Question;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface QuestionService {
    int createQuestion(Question question);
    int deleteQuestion(Long id) throws JsonProcessingException;
    Question getQuestionById(Long id) throws JsonProcessingException;
    List<Question> getQuestionsByKnowledgePointId(Long knowledgePointId);

    List<Question> getAllQuestions();

    public void syncToRedis(Question question);
    List<Question> getQuestionsByQuestionBodyId(Long questionBodyId);

    void deleteFromRedis(Question question);

    List<Question> getQuestionsByKnowledgePointIds(List<Long> knowledgePointIds);

    List<Question> getQuestionsByIds(List<Long> questionIds);

    void flushKnowledgePointCache(Long id);
}

package com.example.service.question;

import com.example.model.question.Question;

import java.util.List;

public interface QuestionService {
    int creatQuestion(Question question);
    int deleteQuestion(Long id);
    Question getQuestionById(Long id);
    List<Question> getQuestionsByKnowledgePointId(Long knowledgePointId);

    List<Question> getAllQuestions();

    List<Question> getQuestionsByQuestionBodyId(Long questionBodyId);
}

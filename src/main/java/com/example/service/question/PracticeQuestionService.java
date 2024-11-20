package com.example.service.question;

import com.example.model.question.PracticeQuestion;

import java.util.List;

public interface PracticeQuestionService {
    int addPracticeQuestion(PracticeQuestion practiceQuestion);
    int deletePracticeQuestion(Long id);
    int deletePracticeQuestionByPracticeId(Long practiceId);
    PracticeQuestion getPracticeQuestionById(Long id);
    List<PracticeQuestion> getPracticeQuestionByPracticeId(Long practiceId);
}

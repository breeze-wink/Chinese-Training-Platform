package com.example.service.submission;

import com.example.model.submission.PracticeAnswer;
import com.example.model.submission.SubmissionAnswer;

public interface PracticeAnswerService {
    int addPracticeAnswer(PracticeAnswer practiceAnswer);
    int deletePracticeAnswer(Long id);
    int updatePracticeAnswer(PracticeAnswer practiceAnswer);
    int deletePracticeAnswerByPracticeQuestionId(Long practiceQuestionId);
    PracticeAnswer getPracticeAnswerById(Long id);
    PracticeAnswer getPracticeAnswerByPracticeQuestionId(Long practiceQuestionId);
}

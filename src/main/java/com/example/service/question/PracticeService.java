package com.example.service.question;

import com.example.model.question.Practice;

import java.util.List;

public interface PracticeService {
    int createPractice(Practice practice);
    int deletePractice(Long id);
    int updatePractice(Practice practice);
    Practice getPracticeById(Long id);
    List<Practice> getPracticesByStudentId(Long studentId);
}
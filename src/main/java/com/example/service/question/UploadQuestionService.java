package com.example.service.question;

import com.example.model.question.UploadQuestion;

import java.util.List;

public interface UploadQuestionService {
    void create(UploadQuestion uploadQuestion);

    void update(UploadQuestion uploadQuestion);

    void delete(Long id);

    UploadQuestion findById(Long id);

    List<UploadQuestion> findByTeacherId(Long teacherId);

    List<UploadQuestion> findAll();

    Long findTeacherIdByQuestionIdAndType(Long questionId, String type);

    List<UploadQuestion> getInSchoolQuestions(Long schoolId);
}
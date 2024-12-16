package com.example.service.question.impl;

import com.example.mapper.question.UploadQuestionMapper;
import com.example.model.question.UploadQuestion;
import com.example.service.question.UploadQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UploadQuestionServiceImpl implements UploadQuestionService {

    private final UploadQuestionMapper uploadQuestionMapper;

    @Autowired
    public UploadQuestionServiceImpl(UploadQuestionMapper uploadQuestionMapper) {
        this.uploadQuestionMapper = uploadQuestionMapper;
    }

    @Override
    @Transactional
    public void create(UploadQuestion uploadQuestion) {
        uploadQuestionMapper.insert(uploadQuestion);
    }

    @Override
    @Transactional
    public void update(UploadQuestion uploadQuestion) {
        uploadQuestionMapper.update(uploadQuestion);
    }

    @Override
    @Transactional

    public void delete(Long questionId, String type) {
        uploadQuestionMapper.delete(questionId, type);
    }

    @Override
    public UploadQuestion findById(Long id) {
        return uploadQuestionMapper.findById(id);
    }

    @Override
    public List<UploadQuestion> findByTeacherId(Long teacherId) {
        return uploadQuestionMapper.findByTeacherId(teacherId);
    }

    @Override
    public List<UploadQuestion> findAll() {
        return uploadQuestionMapper.findAll();
    }

    @Override
    public Long findTeacherIdByQuestionIdAndType(Long questionId, String type) {
        return uploadQuestionMapper.findTeacherIdByQuestionIdAndType(questionId, type);
    }

    @Override
    public List<UploadQuestion> getInSchoolQuestions(Long schoolId) {
        return uploadQuestionMapper.getInSchoolQuestions(schoolId);
    }
}
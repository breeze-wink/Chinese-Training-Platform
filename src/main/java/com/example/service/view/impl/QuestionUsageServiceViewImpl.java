package com.example.service.view.impl;

import com.example.mapper.view.QuestionUsageViewMapper;
import com.example.model.view.QuestionUsageView;
import com.example.service.view.QuestionUsageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionUsageServiceViewImpl implements QuestionUsageViewService {

    private final QuestionUsageViewMapper questionUsageMapper;

    @Autowired
    public QuestionUsageServiceViewImpl(QuestionUsageViewMapper questionUsageMapper) {
        this.questionUsageMapper = questionUsageMapper;
    }

    @Override
    public List<QuestionUsageView> getAllQuestionUsages() {
        return questionUsageMapper.findAll();
    }

    @Override
    public QuestionUsageView getQuestionUsageByIdAndType(Long questionId, String type) {
        return questionUsageMapper.findByIdAndType(questionId, type);
    }
}
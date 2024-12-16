package com.example.service.view;

import com.example.model.view.QuestionUsageView;

import java.util.List;

public interface QuestionUsageViewService {

    /**
     * 获取所有问题使用情况
     *
     * @return List<QuestionUsageView>
     */
    List<QuestionUsageView> getAllQuestionUsages();

    /**
     * 根据 questionId 获取单个问题使用情况
     *
     * @param questionId 问题ID
     * @return QuestionUsageView
     */
    QuestionUsageView getQuestionUsageByIdAndType(Long questionId, String type);
}

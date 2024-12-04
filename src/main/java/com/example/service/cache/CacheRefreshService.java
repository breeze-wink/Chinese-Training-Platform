package com.example.service.cache;

import com.example.service.question.QuestionBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CacheRefreshService {

    private final QuestionBodyService questionBodyService;

    @Autowired
    public CacheRefreshService(QuestionBodyService questionBodyService) {
        this.questionBodyService = questionBodyService;
    }

    // 每小时刷新一次缓存
    @Scheduled(cron = "0 */20 * * * ?")
    public void refreshPreassembledQuestions() {
        questionBodyService.flushPreassembledQuestions();
        System.out.println("预组装题目缓存已刷新");
    }
}

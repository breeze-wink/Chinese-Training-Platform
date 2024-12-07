package com.example.config;

import com.example.service.question.PreAssembledQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CacheInitializer implements ApplicationRunner {

    private final PreAssembledQuestionService preAssembledQuestionService;

    @Autowired
    public CacheInitializer(PreAssembledQuestionService preAssembledQuestionService) {
        this.preAssembledQuestionService = preAssembledQuestionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 在应用启动时刷新预组装缓存
        preAssembledQuestionService.flushPreAssembledQuestions();
        System.out.println("预组装题目缓存已初始化");
    }
}

package com.example.service.question;

import com.example.dto.redis.PreAssembledQuestion;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PreAssembledQuestionService {
    List<PreAssembledQuestion> getPreAssembledQuestionsByTypes(List<String> types);
    void flushPreAssembledQuestions();

    @Transactional
    @Async
    void flushPreAssembledQuestionsByType(String type);
}

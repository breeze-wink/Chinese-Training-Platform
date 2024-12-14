package com.example.service.cache;

import com.example.service.question.PreAssembledQuestionService;
import com.example.service.question.QuestionBodyService;
import com.example.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class CacheRefreshService {

    public ConcurrentHashMap<Long, Boolean> knowledgePointCacheOutOfDate = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Boolean> typeCacheOutOfDate = new ConcurrentHashMap<>();
    private final QuestionBodyService questionBodyService;
    private final PreAssembledQuestionService preAssembledQuestionService;

    private final QuestionService questionService;
    @Autowired
    public CacheRefreshService(QuestionBodyService questionBodyService,
                               PreAssembledQuestionService preAssembledQuestionService,
                               QuestionService questionService) {
        this.questionBodyService = questionBodyService;
        this.preAssembledQuestionService = preAssembledQuestionService;
        this.questionService = questionService;
    }
    public void markKnowledgeCacheOutOfDate(Long id) {
        knowledgePointCacheOutOfDate.put(id, true);
    }
    public void markTypeCacheOutOfDate(String type) {
        typeCacheOutOfDate.put(type, true);
    }

    @Scheduled(fixedRate = 1000 * 10)
    public void refreshKnowledgePointCache() {
        for (Long id : knowledgePointCacheOutOfDate.keySet()) {
            if (knowledgePointCacheOutOfDate.get(id)) {
                questionService.flushKnowledgePointCache(id);
                knowledgePointCacheOutOfDate.put(id, false);
            }
        }
    }
    @Scheduled(fixedRate = 1000 * 10)
    public void refreshTypeCache() {
        for (String type : typeCacheOutOfDate.keySet()) {
            if (typeCacheOutOfDate.get(type)) {
                preAssembledQuestionService.flushPreAssembledQuestionsByType(type);
                typeCacheOutOfDate.put(type, false);
            }
        }
    }
}

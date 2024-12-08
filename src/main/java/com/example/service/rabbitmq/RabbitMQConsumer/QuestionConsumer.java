package com.example.service.rabbitmq.RabbitMQConsumer;
import com.example.config.RabbitMQConfig;
import com.example.model.question.Question;
import com.example.service.cache.CacheRefreshService;
import com.example.service.rabbitmq.dto.QuestionSyncMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.service.question.QuestionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionConsumer {

    private final QuestionService questionService;
    private final ObjectMapper objectMapper;

    private final CacheRefreshService cacheRefreshService;

    @Autowired
    public QuestionConsumer(QuestionService questionService,
                            ObjectMapper objectMapper,
                            CacheRefreshService cacheRefreshService) {
        this.questionService = questionService;
        this.objectMapper = objectMapper;
        this.cacheRefreshService = cacheRefreshService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUESTION_QUEUE_NAME)
    public void receiveMessage(String message) {
        try {
            QuestionSyncMessage syncMessage = objectMapper.readValue(message, QuestionSyncMessage.class);
            String operation = syncMessage.getOperation();
            Question question = syncMessage.getQuestion();
            // 同步到 Redis
            if ("create".equals(operation)) {
                // 处理创建操作
                questionService.syncToRedis(question);
            } else if ("delete".equals(operation)) {
                // 处理删除操作
                questionService.deleteFromRedis(question);
            }
            cacheRefreshService.markKnowledgeCacheOutOfDate(question.getKnowledgePointId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

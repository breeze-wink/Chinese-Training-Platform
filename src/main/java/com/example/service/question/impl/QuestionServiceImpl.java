package com.example.service.question.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.mapper.question.QuestionMapper;
import com.example.model.question.Question;
import com.example.model.view.QuestionUsageView;
import com.example.service.question.QuestionService;
import com.example.service.question.QuestionStatisticService;
import com.example.service.question.UploadQuestionService;
import com.example.service.rabbitmq.RabbitMQProducer;
import com.example.service.view.QuestionUsageViewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class QuestionServiceImpl implements QuestionService {
    private static final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionMapper questionMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitMQProducer rabbitMQProducer;
    private final QuestionStatisticService questionStatisticService;
    private final UploadQuestionService uploadQuestionService;
    private final QuestionUsageViewService questionUsageViewService;

    @Autowired
    public QuestionServiceImpl(QuestionMapper questionMapper,
                               RedisTemplate<String, Object> redisTemplate,
                               RabbitMQProducer rabbitMQProducer,
                               QuestionStatisticService questionStatisticService,
                               UploadQuestionService uploadQuestionService,
                               QuestionUsageViewService questionUsageViewService) {
        this.questionMapper = questionMapper;
        this.redisTemplate = redisTemplate;
        this.rabbitMQProducer = rabbitMQProducer;
        this.questionStatisticService = questionStatisticService;
        this.uploadQuestionService = uploadQuestionService;
        this.questionUsageViewService = questionUsageViewService;
    }

    @Override
    @Transactional
    public int createQuestion(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    public void outdateQuestion(Question question) {
        questionMapper.outDate(question.getId());
    }

    @Override
    public void updateQuestion(Question question) {
        questionMapper.update(question);
    }

    @Override
    @Transactional
    public void access(Question question) {
        questionMapper.access(question.getId());
        // 发送同步消息
        rabbitMQProducer.sendQuestionSyncMessage(question, RabbitMQProducer.CREATE_OPERATION);
    }

    @Override
    @Transactional
    public void deny(Question question) {
        questionMapper.deny(question.getId());
        // 发送同步消息
        rabbitMQProducer.sendQuestionSyncMessage(question, RabbitMQProducer.CREATE_OPERATION);
    }
    @Override
    @Transactional
    public int deleteQuestion(Question question) {
        Long id = question.getId();
        int result;
        if (question.getBodyId() != null || checkQuestionNotUsed(question)) {
            questionStatisticService.delete(id, "small");
            uploadQuestionService.delete(id, "small");
            rabbitMQProducer.sendQuestionSyncMessage(question, RabbitMQProducer.DELETE_OPERATION);
            fileRemove(question.getContent());
            result = questionMapper.reallyDelete(id);
        }
        else {
            result = questionMapper.delete(id);
        }
        // 发送同步消息
        rabbitMQProducer.sendQuestionSyncMessage(question, RabbitMQProducer.DELETE_OPERATION);
//        if (question.getBodyId() != null) {
//            String cacheKey = "questions:questionBody:" + question.getBodyId();
//            redisTemplate.delete(cacheKey);
//        }
        return result;
    }

    private boolean checkQuestionNotUsed(Question question) {
        QuestionUsageView  questionUsageView = questionUsageViewService.getQuestionUsageByIdAndType(question.getId(),"small");
        return !questionUsageView.getUsed();
    }

    @Override
    @Transactional(readOnly = true)
    public Question getQuestionById(Long id) {
        // 尝试从缓存获取
        String cacheKey = "question:" + id;
        Object object = redisTemplate.opsForValue().get(cacheKey);
        ObjectMapper objectMapper = new ObjectMapper();
        if (object != null) {
            return objectMapper.convertValue(object, Question.class);
        }
        // 从数据库获取并缓存
        Question question = questionMapper.selectById(id);
        if (question != null) {
            redisTemplate.opsForValue().set(cacheKey, question);
        }
        return question;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestionsByIds(List<Long> questionIds) {
        List<Question> questions = new ArrayList<>();
        for (Long id : questionIds) {
            questions.add(getQuestionById(id));
        }
        return questions;
    }

    @Override
    @Transactional
    @Async
    public void flushKnowledgePointCache(Long id) {
        String cacheKey = "questions:knowledgePoint:" + id;
        List<Question> questions = questionMapper.getQuestionsByKnowledgePointId(id);
        if (questions != null && !questions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, questions);
        }
    }

    @Override
    public List<Question> getQuestionsByKnowledgePointId(Long knowledgePointId) {
        // 尝试从缓存获取
        String cacheKey = "questions:knowledgePoint:" + knowledgePointId;
        List<?> cachedQuestions = (List<?>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedQuestions != null) {
            // 将缓存中的数据转换为 List<Question>
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.convertValue(cachedQuestions, objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class));
            } catch (Exception e) {
                // 记录日志
                log.error("Failed to convert cached questions to List<Question> for knowledgePointId: {}", knowledgePointId, e);
            }
        }

        // 从数据库获取并缓存
        List<Question> questions = questionMapper.getQuestionsByKnowledgePointId(knowledgePointId);
        if (questions != null && !questions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, questions);
        }
        return questions;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionMapper.selectAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestionsByQuestionBodyId(Long questionBodyId) {
        // 尝试从缓存获取
        String cacheKey = "questions:questionBody:" + questionBodyId;
        List<?> cachedQuestions = (List<?>) redisTemplate.opsForValue().get(cacheKey);
        if (cachedQuestions != null) {
            // 将缓存中的数据转换为 List<Question>
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.convertValue(cachedQuestions, objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class));
            } catch (Exception e) {
                // 记录日志
                log.error("Failed to convert cached questions to List<Question> for questionBodyId: {}", questionBodyId, e);
            }
        }

        // 从数据库获取并缓存
        List<Question> questions = questionMapper.getQuestionsByQuestionBodyId(questionBodyId);
        if (questions != null && !questions.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, questions);
        }
        return questions;
    }



    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestionsByKnowledgePointIds(List<Long> knowledgePointIds) {
        List<Question> questions = new ArrayList<>();
        for (Long knowledgePointId : knowledgePointIds) {
            questions.addAll(getQuestionsByKnowledgePointId(knowledgePointId));
        }
        return questions;
    }


    @Override
    public void deleteFromRedis(Question question) {
        String cacheKey = "question:" + question.getId();
        redisTemplate.delete(cacheKey);
        cacheKey = "questions:knowledgePoint:" + question.getKnowledgePointId();
        redisTemplate.delete(cacheKey);
    }
    @Override
    public void syncToRedis(Question question) {
        String cacheKey = "question:" + question.getId();
        redisTemplate.opsForValue().set(cacheKey, question);
        cacheKey = "questions:knowledgePoint:" + question.getKnowledgePointId();
        redisTemplate.delete(cacheKey);
    }
    /**
     * 删除HTML内容中引用的图片文件
     *
     * @param body 包含图片的HTML文本
     */
    @Override
    public void fileRemove(String body) {
        if (body == null || body.isEmpty()) {
            log.warn("传入的HTML内容为空");
            return;
        }
        final String uploadDir = "uploads";  // 上传目录

        // 正则表达式匹配 <img> 标签的 src 属性
        String imgTagPattern = "<img[^>]+src=\"([^\"]+)\"[^>]*>";
        Pattern pattern = Pattern.compile(imgTagPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(body);

        while (matcher.find()) {
            String src = matcher.group(1); // 获取 src 属性的值
            if (src == null || src.isEmpty()) {
                continue;
            }

            // 处理相对路径，去除开头的 '/uploads/' 或 '/'
            if (src.startsWith("/uploads/")) {
                src = src.substring("/uploads/".length());
            } else if (src.startsWith("/")) {
                src = src.substring(1);
            }

            // 构建文件的绝对路径
            Path filePath = Paths.get(uploadDir).resolve(src).normalize();

            // 安全检查，确保文件在指定的上传目录下
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path absoluteFilePath = filePath.toAbsolutePath().normalize();

            if (!absoluteFilePath.startsWith(uploadPath)) {
                // 跳过不在上传目录下的文件
                log.warn("尝试删除上传目录之外的文件: {}", absoluteFilePath);
                continue;
            }

            File file = filePath.toFile();
            if (file.exists()) {
                boolean deleted = file.delete();
                if (deleted) {
                    log.info("已删除文件: {}", absoluteFilePath);
                } else {
                    log.error("删除文件失败: {}", absoluteFilePath);
                }
            } else {
                log.warn("文件不存在: {}", absoluteFilePath);
            }
        }
    }

}

package com.example.service.course.impl;

import com.example.dto.response.teacher.KnowledgePointsResponse;
import com.example.dto.response.teacher.ListKnowledgeResponse;
import com.example.mapper.course.KnowledgePointMapper;
import com.example.model.course.KnowledgePoint;
import com.example.service.course.KnowledgePointService;
import com.example.service.rabbitmq.RabbitMQProducer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KnowledgePointServiceImpl implements KnowledgePointService {
    private final KnowledgePointMapper knowledgePointMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public KnowledgePointServiceImpl(KnowledgePointMapper knowledgePointMapper,
                                     RabbitMQProducer rabbitMQProducer,
                                     RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.knowledgePointMapper = knowledgePointMapper;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    @Transactional
    public int addKnowledgePoint(KnowledgePoint point) {
        if (point == null) {
            throw new IllegalArgumentException("KnowledgePoint object cannot be null");
        }
        return knowledgePointMapper.insert(point);
    }

    @Override
    @Transactional
    public int removeKnowledgePoint(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID cannot be null or less than or equal to zero");
        }
        // 检查是否有其他记录引用了这个知识点

        return knowledgePointMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateKnowledgePoint(KnowledgePoint point) {
        if (point == null || point.getId() == null || point.getId() <= 0) {
            throw new IllegalArgumentException("KnowledgePoint object must have a valid ID");
        }
        return knowledgePointMapper.update(point);
    }

    @Override
    public KnowledgePoint getKnowledgePointById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID cannot be null or less than or equal to zero");
        }
        return knowledgePointMapper.selectById(id);
    }

    @Override
    public String getKnowledgePointNameById(Long id) {
        return knowledgePointMapper.selectById(id).getName();
    }

    @Override
    public List<KnowledgePoint> getAllKnowledgePoints() {
        return knowledgePointMapper.selectAll();
    }

    @Override
    public Map<String, List<ListKnowledgeResponse.KnowledgePointInfo>> getAllKnowledgePointsGroupByType() {
        String cacheKey = "knowledgePointsGroupByType";
        Object object = redisTemplate.opsForValue().get(cacheKey);
        if (object == null) {
            flushKnowledgePointCache();
            object = redisTemplate.opsForValue().get(cacheKey);
        }
        return objectMapper.convertValue(object, new TypeReference<Map<String, List<ListKnowledgeResponse.KnowledgePointInfo>>>() {});

    }

    @Override
    public Map<String, List<KnowledgePointsResponse.KnowledgePointInfo>> getAllKnowledgePointsWithDescriptionGroupByType() {
        String cacheKey = "knowledgePointsGroupWithDescriptionByType";
        Object object = redisTemplate.opsForValue().get(cacheKey);
        if (object == null) {
            flushKnowledgePointCache();
            object = redisTemplate.opsForValue().get(cacheKey);

        }
        return objectMapper.convertValue(object, new TypeReference<Map<String, List<KnowledgePointsResponse.KnowledgePointInfo>>>() {});
    }


    @Override
    public void flushKnowledgePointCache() {
        List<KnowledgePoint> list = knowledgePointMapper.selectAllOrderByType();
        Map<String, List<ListKnowledgeResponse.KnowledgePointInfo>> groupedPoints =
                list.stream()
                        .collect(Collectors.groupingBy(KnowledgePoint::getType))  // 先按 KnowledgePoint 的 type 进行分组
                        .entrySet().stream()  // 获取分组后的 EntrySet
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,  // 使用 type 作为键
                                entry -> entry.getValue().stream()
                                        .map(kp -> {
                                            ListKnowledgeResponse.KnowledgePointInfo info = new ListKnowledgeResponse.KnowledgePointInfo();
                                            info.setName(kp.getName());
                                            info.setId(kp.getId());
                                            return info;
                                        })
                                        .collect(Collectors.toList())  // 转换为 List<KnowledgePointInfo>
                        ));

        // 将结果存入缓存
        String cacheKey1 = "knowledgePointsGroupByType";
        redisTemplate.opsForValue().set(cacheKey1, groupedPoints);

        Map<String, List<KnowledgePointsResponse.KnowledgePointInfo>> groupedPointsWithDescription =
                list.stream()
                        .collect(Collectors.groupingBy(KnowledgePoint::getType))  // 先按 KnowledgePoint 的 type 进行分组
                        .entrySet().stream()  // 获取分组后的 EntrySet
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,  // 使用 type 作为键
                                entry -> entry.getValue().stream()
                                        .map(kp -> {
                                            KnowledgePointsResponse.KnowledgePointInfo info = new KnowledgePointsResponse.KnowledgePointInfo();
                                            info.setName(kp.getName());
                                            info.setType(kp.getType());
                                            info.setDescription(kp.getDescription());
                                            return info;
                                        })
                                        .collect(Collectors.toList())  // 转换为 List<KnowledgePointInfo>
                        ));
        String cacheKey2 = "knowledgePointsGroupWithDescriptionByType";
        redisTemplate.opsForValue().set(cacheKey2, groupedPointsWithDescription);
    }



}
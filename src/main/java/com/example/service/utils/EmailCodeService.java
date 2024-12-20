package com.example.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmailCodeService {
    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    public EmailCodeService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getCode(String type, String email) {
        String cacheKey = type + "Email:" + email;
        Object object = redisTemplate.opsForValue().get(cacheKey);
        ObjectMapper objectMapper = new ObjectMapper();
        if (object != null) {
            return objectMapper.convertValue(object, String.class);
        }
        return null;
    }

    public void setCode(String type, String email, String code) {
        String cacheKey = type + "Email:" + email;
        redisTemplate.opsForValue().set(cacheKey, code, 5, TimeUnit.MINUTES);
    }

    public String getFindPasswordCode(String type, String email) {
        String cacheKey = type + "FindPasswordCode:" + email;
        Object object = redisTemplate.opsForValue().get(cacheKey);
        ObjectMapper objectMapper = new ObjectMapper();
        if (object != null) {
            return objectMapper.convertValue(object, String.class);
        }
        return null;
    }

    public void setFindPasswordCode(String type, String email, String code) {
        String cacheKey = type + "FindPasswordCode:" + email;
        redisTemplate.opsForValue().set(cacheKey, code, 5, TimeUnit.MINUTES);
    }
}

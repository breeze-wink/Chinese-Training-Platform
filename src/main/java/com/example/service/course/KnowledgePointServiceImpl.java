package com.example.service.course;

import com.example.mapper.course.KnowledgePointMapper;
import com.example.model.course.KnowledgePoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KnowledgePointServiceImpl implements KnowledgePointService {

    @Autowired
    private KnowledgePointMapper knowledgePointMapper;

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
        return knowledgePointMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateKnowledgePoint(KnowledgePoint point) {
        if (point == null) {
            throw new IllegalArgumentException("KnowledgePoint object cannot be null");
        }
        if (point.getId() == null || point.getId() <= 0) {
            throw new IllegalArgumentException("KnowledgePoint ID must be set and greater than zero");
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
    public List<KnowledgePoint> getAllKnowledgePoints() {
        return knowledgePointMapper.selectAll();
    }
}
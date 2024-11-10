package com.example.service.course;

import com.example.model.course.KnowledgePoint;

import java.util.List;

public interface KnowledgePointService {
    int addKnowledgePoint(KnowledgePoint point);
    int removeKnowledgePoint(Long id);
    int updateKnowledgePoint(KnowledgePoint point);
    KnowledgePoint getKnowledgePointById(Long id);
    List<KnowledgePoint> getAllKnowledgePoints();
}
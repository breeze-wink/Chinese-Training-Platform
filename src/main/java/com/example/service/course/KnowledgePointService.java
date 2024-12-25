package com.example.service.course;

import com.example.dto.response.teacher.KnowledgePointsResponse;
import com.example.dto.response.teacher.ListKnowledgeResponse;
import com.example.model.course.KnowledgePoint;

import java.util.List;
import java.util.Map;

public interface KnowledgePointService {
    int addKnowledgePoint(KnowledgePoint point);
    int removeKnowledgePoint(Long id);
    int updateKnowledgePoint(KnowledgePoint point);
    KnowledgePoint getKnowledgePointById(Long id);

    String getKnowledgePointNameById(Long id);
    List<KnowledgePoint> getAllKnowledgePoints();

    List<String> getAllTypes();

    boolean isExist(KnowledgePoint knowledgePoint);

    Map<String, List<ListKnowledgeResponse.KnowledgePointInfo>> getAllKnowledgePointsGroupByType();

    void flushKnowledgePointCache();

    Map<String, List<KnowledgePointsResponse.KnowledgePointInfo>> getAllKnowledgePointsWithDescriptionGroupByType();


    void deleteFromRedis(KnowledgePoint knowledgePoint);

    void syncToRedis(KnowledgePoint knowledgePoint);

}
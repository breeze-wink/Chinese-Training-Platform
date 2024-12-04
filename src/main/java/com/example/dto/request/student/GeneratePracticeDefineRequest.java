package com.example.dto.request.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneratePracticeDefineRequest {
    private String name;
    private List<KnowledgePoint> knowledgePoints;
    private List<QuestionBodyType> questionBodyTypes;

    @Setter
    @Getter
    public static class KnowledgePoint {
        private Long knowledgePointId;
        private Integer num;
    }

    @Setter
    @Getter
    public static class QuestionBodyType {
        private String type;
        private Integer num;
    }
}

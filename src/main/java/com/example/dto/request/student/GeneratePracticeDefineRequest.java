package com.example.dto.request.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneratePracticeDefineRequest {
    private Integer num;
    private String name;
    private List<InfoData> data;

    @Setter
    @Getter
    public static class InfoData {
        private Long knowledgePointId;
    }
}

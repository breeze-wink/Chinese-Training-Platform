package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MarkSubmissionRequest {
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private Long submissionAnswerId;
        private Integer markScore;
        private String feedback;
    }
}

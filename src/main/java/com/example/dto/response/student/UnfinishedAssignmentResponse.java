package com.example.dto.response.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UnfinishedAssignmentResponse {
    private String message;
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private Long assignmentId;
        private String title;
        private String description;
        private String startTime;
        private String endTime;
    }
}

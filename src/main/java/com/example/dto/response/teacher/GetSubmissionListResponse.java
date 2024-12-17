package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetSubmissionListResponse {
    private String message;
    private Long assignmentId;
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private Long studentId;
        private String studentName;
        private int isSubmitted;
        private String submitTime;
        private Integer totalScore;
        private int isMarked;
    }
}

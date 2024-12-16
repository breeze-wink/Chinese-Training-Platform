package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AssignmentListResponse {
    private String message;
    private List<infoData> data;

    @Setter
    @Getter
    public static class infoData {
        private Long assignmentId;
        private String assignmentTitle;
        private String assignmentDescription;
        private String startTime;
        private String endTime;
        private Long paperId;
    }
}

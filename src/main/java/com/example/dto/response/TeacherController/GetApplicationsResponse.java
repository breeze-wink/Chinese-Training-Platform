package com.example.dto.response.TeacherController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetApplicationsResponse {
    private String message;
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private Long joinClassId;
        private Long studentId;
        private String userName;
        private String name;
    }
}

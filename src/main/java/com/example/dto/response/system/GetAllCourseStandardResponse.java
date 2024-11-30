package com.example.dto.response.system;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetAllCourseStandardResponse {
    private String message;
    private List<CourseStandardInfo> courseStandardInfos;
    @Getter
    @Setter
    public static class CourseStandardInfo {
        private Long id;
        private String title;
        private String executedDate;
    }
}

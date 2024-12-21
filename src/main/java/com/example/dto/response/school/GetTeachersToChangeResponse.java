package com.example.dto.response.school;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetTeachersToChangeResponse {
    private String message;
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private Long teacherId;
        private String teacherName;
    }
}

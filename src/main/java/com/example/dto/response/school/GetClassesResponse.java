package com.example.dto.response.school;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetClassesResponse {
    private String message;
    private List<infoData> data;

    @Getter
    @Setter
    public static class infoData {
        private Long classId;
        private String name;
        private String teacherName;
        private String inviteCode;
    }
}

package com.example.dto.response.TeacherController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeacherGetClassMembersResponse {
    private String message;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long studentId;
        private String studentName;
    }
}

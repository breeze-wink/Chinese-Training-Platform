package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SchoolAdminQueryTeachers {
    private String message;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long id;
        private String name;
        private String email;
        private String phoneNumber;
        private Long schoolId;
    }
}

package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolAdminQueryTeacher {
    private String message;
    private InfoData data;

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

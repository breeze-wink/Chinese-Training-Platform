package com.example.dto.response.SchoolAdminController;

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
        private String username;
        private Long id;
        private String name;
        private String email;
        private String phoneNumber;
        private Long schoolId;
    }
}

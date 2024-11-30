package com.example.dto.response.SchoolAdminController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SchoolAdminQueryStudents {
    private String message;
    private List<InfoData> data;

    @Getter
    @Setter
    public static class InfoData {
        private Long id;
        private String name;
        private String username;
        private String email;
        private Integer grade;
        private Long schoolId;
    }
}

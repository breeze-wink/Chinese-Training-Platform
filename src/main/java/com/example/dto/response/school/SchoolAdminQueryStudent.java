package com.example.dto.response.school;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolAdminQueryStudent {
    private String message;
    private InfoData data;

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

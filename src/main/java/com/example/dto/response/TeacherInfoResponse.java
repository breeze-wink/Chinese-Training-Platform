package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherInfoResponse {
    private String message;
    private InfoData data;

    @Getter
    @Setter
    public static class InfoData {
        private String name;
        private String username;
        private String email;
        private String phoneNumber;
        private String schoolName;
    }
}

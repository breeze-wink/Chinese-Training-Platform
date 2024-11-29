package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SchoolAdminInfoResponse {
    private String message;
    private InfoData data;

    @Getter
    @Setter
    public static class InfoData {
        private String username;
        private String email;
        private String name;
        private String schoolName;
        private String authorizationCode;
        private String createDate;
    }
}

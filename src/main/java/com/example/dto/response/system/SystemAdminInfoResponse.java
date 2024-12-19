package com.example.dto.response.system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemAdminInfoResponse {
    private String message;
    private InfoData data;

    @Getter
    @Setter
    public static class InfoData {
        private Long id;
        private String username;
        private String email;
    }
}

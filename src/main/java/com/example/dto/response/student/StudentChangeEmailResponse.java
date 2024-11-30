package com.example.dto.response.student;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentChangeEmailResponse {
    private String message;
    private InfoData data;
    @Getter
    @Setter
    public static class InfoData {
        private String email;
    }
}

package com.example.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentJoinClassResponse {
    private String message;
    private InfoData data;
    @Getter
    @Setter
    public static class InfoData {
        private String className;
    }
}

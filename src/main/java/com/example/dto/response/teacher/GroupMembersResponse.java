package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GroupMembersResponse {
    private String message;
    private List<memberInfo> students;

    @Getter
    @Setter
    public static class memberInfo {
        private Long studentId;
        private String studentName;
    }
}

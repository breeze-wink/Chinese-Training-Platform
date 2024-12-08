package com.example.dto.response.school;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClassDetailResponse {
    private String message;
    private infoData data;

    @Setter
    @Getter
    public static class infoData {
        private String className;
        private String classDescription;
        private List<GroupInfo> groups;
        private List<StudentInfo> students;
    }

    @Getter
    @Setter
    public static class GroupInfo {
        private Long groupId;
        private String groupName;
        private String groupDescription;
    }

    @Getter
    @Setter
    public static class StudentInfo {
        private Long studentId;
        private String studentName;
    }
}

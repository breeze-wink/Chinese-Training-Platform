package com.example.dto.response.teacher;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetGroupsResponse {
    private String message; // 响应消息

    private List<GroupInfo> data; // 小组信息列表

    @Setter
    @Getter
    public static class GroupInfo {
        private Long groupId; // 小组ID
        private String groupName; // 小组名称
        private String className; // 所属班级名称 (可选)
        private String groupDescription; // 小组描述
    }
}

package com.example.dto.request.TeacherController;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CreateGroupRequest {
    private Long classId; // 所属班级的唯一标识符
    private String groupName; // 小组名称
    private List<Long> studentIds; // 学生ID的列表
    private String groupDescription; // 小组描述
}

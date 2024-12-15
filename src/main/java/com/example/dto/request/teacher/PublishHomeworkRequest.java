package com.example.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class PublishHomeworkRequest {
    private String name;
    private Long referencedPaperId;
    private List<Long> targetIds;
    private String targetType;
    private LocalDateTime publishTime;
    private LocalDateTime dueTime;
    private String description;
}

package com.example.model.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Notification {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private Long creatorId; // 外键，关联Teacher表的id

}

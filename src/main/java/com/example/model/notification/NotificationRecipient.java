package com.example.model.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRecipient {
    private Long notificationId;
    private Long studentId;
    private Boolean isRead;

}

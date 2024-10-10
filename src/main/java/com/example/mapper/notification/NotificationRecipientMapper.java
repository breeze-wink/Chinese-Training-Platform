package com.example.mapper.notification;

import com.example.model.notification.NotificationRecipient;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationRecipientMapper {

    @Insert("INSERT INTO notification_recipient(notification_id, student_id, is_read) VALUES(#{notificationId}, #{studentId}, #{isRead})")
    int insert(NotificationRecipient recipient);

    @Delete("DELETE FROM notification_recipient WHERE notification_id = #{notificationId} AND student_id = #{studentId}")
    int delete(@Param("notificationId") Long notificationId, @Param("studentId") Long studentId);

    @Update("UPDATE notification_recipient SET is_read = #{isRead} WHERE notification_id = #{notificationId} AND student_id = #{studentId}")
    int update(NotificationRecipient recipient);

    @Select("SELECT * FROM notification_recipient WHERE notification_id = #{notificationId} AND student_id = #{studentId}")
    NotificationRecipient select(@Param("notificationId") Long notificationId, @Param("studentId") Long studentId);

    @Select("SELECT * FROM notification_recipient WHERE student_id = #{studentId}")
    List<NotificationRecipient> selectByStudentId(Long studentId);
}

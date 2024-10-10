package com.example.mapper.notification;

import com.example.model.notification.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("INSERT INTO notification(title, content, create_time, creator_id, class_id) VALUES(#{title}, #{content}, #{createTime}, #{creatorId}, #{classId})")
    int insert(Notification notification);

    @Delete("DELETE FROM notification WHERE id = #{id}")
    int delete(Long id);

    @Update("UPDATE notification SET title = #{title}, content = #{content}, create_time = #{createTime}, creator_id = #{creatorId}, class_id = #{classId} WHERE id = #{id}")
    int update(Notification notification);

    @Select("SELECT * FROM notification WHERE id = #{id}")
    Notification selectById(Long id);

    @Select("SELECT * FROM notification")
    List<Notification> selectAll();
}

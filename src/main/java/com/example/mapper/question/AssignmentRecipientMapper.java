package com.example.mapper.question;

import com.example.model.question.AssignmentRecipient;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentRecipientMapper {

    @Insert("INSERT INTO assignment_recipient(assignmentId, recipientType, recipientId) " +
            "VALUES(#{assignmentId}, #{recipientType}, #{recipientId})")
    int insert(AssignmentRecipient assignmentRecipient);

    @Delete("DELETE FROM assignment_recipient WHERE assignmentId = #{assignmentId} " +
            "AND recipientType = #{recipientType} AND recipientId = #{recipientId}")
    int delete(@Param("assignmentId") Long assignmentId,
               @Param("recipientType") String recipientType,
               @Param("recipientId") Long recipientId);

    @Select("SELECT * FROM assignment_recipient WHERE assignmentId = #{assignmentId} " +
            "AND recipientType = #{recipientType} AND recipientId = #{recipientId}")
    AssignmentRecipient selectByIds(@Param("assignmentId") Long assignmentId,
                                    @Param("recipientType") String recipientType,
                                    @Param("recipientId") Long recipientId);

    @Select("SELECT * FROM assignment_recipient")
    List<AssignmentRecipient> selectAll();

    @Select("SELECT * FROM assignment_recipient WHERE assignmentId = #{assignmentId}")
    List<AssignmentRecipient> selectByAssignmentId(Long assignmentId);

    @Select("SELECT * FROM assignment_recipient WHERE recipientType = #{recipientType} " +
            "AND recipientId = #{recipientId}")
    List<AssignmentRecipient> selectByRecipient(@Param("recipientType") String recipientType,
                                                @Param("recipientId") Long recipientId);

    void batchInsert(List<AssignmentRecipient> assignmentRecipientList);
}

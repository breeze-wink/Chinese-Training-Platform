package com.example.mapper.question;

import com.example.model.question.ApproveQuestion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApproveQuestionMapper {

    // 插入审核记录
    @Insert("INSERT INTO approve_question (uploadId, comment, status, executeTeacherId) VALUES (#{uploadId}, #{comment}, #{status}, #{executeTeacherId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")  // 自动生成主键
    int insert(ApproveQuestion approveQuestion);

    // 根据 ID 查询审核记录
    @Select("SELECT * FROM approve_question WHERE id = #{id}")
    ApproveQuestion selectById(Long id);

    // 根据上传 ID 查询所有审核记录
    @Select("SELECT * FROM approve_question WHERE uploadId = #{uploadId}")
    List<ApproveQuestion> selectByUploadId(Long uploadId);

    // 更新审核状态
    @Update("UPDATE approve_question SET executeTeacherId = #{executeTeacherId},comment = #{comment}, status = #{status} WHERE id = #{id}")
    int update(ApproveQuestion approveQuestion);

    // 删除审核记录
    @Delete("DELETE FROM approve_question WHERE id = #{id}")
    int delete(Long id);
}
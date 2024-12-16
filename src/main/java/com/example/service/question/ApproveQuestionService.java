package com.example.service.question;

import com.example.model.question.ApproveQuestion;

import java.util.List;

public interface ApproveQuestionService {

    // 插入审核记录
    int insert(ApproveQuestion approveQuestion);

    // 根据 ID 查询审核记录
    ApproveQuestion selectById(Long id);

    // 根据上传 ID 查询所有审核记录
    List<ApproveQuestion> selectByUploadId(Long uploadId);

    // 更新审核记录
    int update(ApproveQuestion approveQuestion);

    // 删除审核记录
    int delete(Long id);
}
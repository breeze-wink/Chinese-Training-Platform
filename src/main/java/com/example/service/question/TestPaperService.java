package com.example.service.question;

import com.example.model.question.TestPaper;

import java.util.List;

public interface TestPaperService {

    int insert(TestPaper testPaper);

    int delete(Long id);

    int update(TestPaper testPaper);

    TestPaper selectById(Long id);

    List<TestPaper> selectAll();

    List<TestPaper> selectByCreatorId(Long creatorId);
}

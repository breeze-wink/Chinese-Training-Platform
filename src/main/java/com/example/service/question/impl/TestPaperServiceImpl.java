package com.example.service.question.impl;

import com.example.mapper.question.TestPaperMapper;
import com.example.model.question.TestPaper;
import com.example.service.question.TestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestPaperServiceImpl implements TestPaperService {

    @Autowired
    private TestPaperMapper testPaperMapper;

    @Override
    public int insert(TestPaper testPaper) {
        return testPaperMapper.insert(testPaper);
    }

    @Override
    public int delete(Long id) {
        return testPaperMapper.delete(id);
    }

    @Override
    public int update(TestPaper testPaper) {
        return testPaperMapper.update(testPaper);
    }

    @Override
    public TestPaper selectById(Long id) {
        return testPaperMapper.selectById(id);
    }

    @Override
    public List<TestPaper> selectAll() {
        return testPaperMapper.selectAll();
    }

    @Override
    public List<TestPaper> selectByCreatorId(Long creatorId) {
        return testPaperMapper.selectByCreatorId(creatorId);
    }
}

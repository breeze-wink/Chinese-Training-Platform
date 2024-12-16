package com.example.service.question.impl;

import com.example.dto.mapper.QuestionStatisticDTO;
import com.example.mapper.question.QuestionBodyMapper;
import com.example.mapper.question.QuestionMapper;
import com.example.model.question.Question;
import com.example.model.question.QuestionBody;
import com.example.model.question.QuestionStatistic;
import com.example.mapper.question.QuestionStatisticMapper;
import com.example.service.question.QuestionStatisticService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class QuestionStatisticServiceImpl implements QuestionStatisticService {

    @Autowired
    private QuestionStatisticMapper questionStatisticMapper;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionBodyMapper questionBodyMapper;

    @Override
    @Transactional
    public void insert(QuestionStatistic questionStatistic) {
        questionStatisticMapper.insert(questionStatistic);
    }

    @Override
    @Transactional
    public void update(QuestionStatistic questionStatistic) {
        questionStatisticMapper.update(questionStatistic);
    }

    @Override
    @Transactional
    public void delete(Long id, String type) {
        questionStatisticMapper.delete(id, type);
    }

    @Transactional
    @Override
    public void addReferencedCount(List<QuestionStatisticDTO> questionStatisticDTOS) {
        try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            QuestionStatisticMapper mapper = session.getMapper(QuestionStatisticMapper.class);
            for (QuestionStatisticDTO question : questionStatisticDTOS) {
                mapper.addReferencedCount(question);
            }
            session.commit();
        }
    }

    @Override
    public QuestionStatistic findByIdAndType(Long id, String type) {
        return questionStatisticMapper.findByIdAndType(id, type);
    }

    @Override
    public Date getUploadTime(Long id, String type) {
        return questionStatisticMapper.selectUploadTime(id, type);
    }

    @Override
    public List<QuestionStatistic> findAll() {
        return questionStatisticMapper.findAll();
    }

    @Override
    public boolean checkQuestionPassed(Long questionId, String type) {
        if (type.equals("small")) {
            int status = questionMapper.getStatus(questionId);
            return status == Question.STATUS_ACCESS;
        }
        else {
            int status = questionBodyMapper.getStatus(questionId);
            return status == QuestionBody.STATUS_ACCESS;
        }
    }
}
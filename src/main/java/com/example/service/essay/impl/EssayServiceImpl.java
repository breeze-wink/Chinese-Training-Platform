package com.example.service.essay.impl;

import com.example.mapper.essay.EssayMapper;
import com.example.model.essay.Essay;
import com.example.service.essay.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EssayServiceImpl implements EssayService {

    private final EssayMapper essayMapper;

    @Autowired
    public EssayServiceImpl(EssayMapper essayMapper) {
        this.essayMapper = essayMapper;
    }

    @Override
    public Essay createEssay(Essay essay) {
        essayMapper.insertEssay(essay);
        return essay;
    }

    @Override
    public Essay getEssayById(Long id) {
        return essayMapper.getEssayById(id);
    }

    @Override
    public int updateEssay(Essay essay) {
        return essayMapper.updateEssay(essay);
    }

    @Override
    public int deleteEssay(Long id) {
        return essayMapper.deleteEssay(id);
    }

    @Override
    public List<Essay> getAllEssays() {
        return essayMapper.getAllEssays();
    }
}

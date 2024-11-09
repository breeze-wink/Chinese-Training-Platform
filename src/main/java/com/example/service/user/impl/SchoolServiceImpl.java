package com.example.service.user.impl;

import com.example.mapper.user.SchoolMapper;
import com.example.model.user.School;
import com.example.service.user.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    private final SchoolMapper schoolMapper;
    @Autowired
    public SchoolServiceImpl (SchoolMapper schoolMapper) {
        this.schoolMapper = schoolMapper;
    }

    @Override
    @Transactional
    public int addSchool(School school) {
        return schoolMapper.insert(school);
    }

    @Override
    @Transactional
    public int removeSchool(Long id) {
        return schoolMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateSchool(School school) {
        return schoolMapper.update(school);
    }

    @Override
    public School getSchoolById(Long id) {
        return schoolMapper.selectById(id);
    }

    @Override
    public List<School> getAllSchools() {
        return schoolMapper.selectAll();
    }
}

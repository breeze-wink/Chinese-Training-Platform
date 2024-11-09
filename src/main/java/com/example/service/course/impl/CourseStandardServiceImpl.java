package com.example.service.course.impl;

import com.example.mapper.course.CourseStandardMapper;
import com.example.model.course.CourseStandard;
import com.example.service.course.CourseStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseStandardServiceImpl implements CourseStandardService {

    @Autowired
    private CourseStandardMapper courseStandardMapper;

    @Override
    @Transactional
    public int addCourseStandard(CourseStandard standard) {
        if (standard == null) {
            throw new IllegalArgumentException("CourseStandard object cannot be null");
        }
        return courseStandardMapper.insert(standard);
    }

    @Override
    @Transactional
    public int removeCourseStandard(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID cannot be null or less than or equal to zero");
        }
        return courseStandardMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateCourseStandard(CourseStandard standard) {
        if (standard == null || standard.getId() == null || standard.getId() <= 0) {
            throw new IllegalArgumentException("CourseStandard object must have a valid ID");
        }
        return courseStandardMapper.update(standard);
    }

    @Override
    public CourseStandard getCourseStandardById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID cannot be null or less than or equal to zero");
        }
        return courseStandardMapper.selectById(id);
    }

    @Override
    public List<CourseStandard> getAllCourseStandards() {
        return courseStandardMapper.selectAll();
    }
}
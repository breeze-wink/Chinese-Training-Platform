package com.example.service.user;

import com.example.model.user.School;

import java.util.List;

public interface SchoolService {
    int addSchool(School school);
    int removeSchool(Long id);
    int updateSchool(School school);
    School getSchoolById(Long id);
    List<School> getAllSchools();
}

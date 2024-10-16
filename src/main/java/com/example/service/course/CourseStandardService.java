package com.example.service.course;

import com.example.model.course.CourseStandard;
import java.util.List;

public interface CourseStandardService {
    int addCourseStandard(CourseStandard standard);
    int removeCourseStandard(Long id);
    int updateCourseStandard(CourseStandard standard);
    CourseStandard getCourseStandardById(Long id);
    List<CourseStandard> getAllCourseStandards();
}
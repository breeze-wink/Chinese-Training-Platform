package com.example.service.course;

import com.example.model.course.CourseStandard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class CourseStandardServiceImplTest {

    @Autowired
    private CourseStandardService courseStandardService;

    private CourseStandard standard1;
    private Long standard1Id;

    @BeforeEach
    void setUp() {
        standard1 = new CourseStandard();
        standard1.setTitle("Standard 1");
        standard1.setDescription("Description for Standard 1");
    }

    @AfterEach
    void tearDown() {
        if (standard1Id != null) {
            courseStandardService.removeCourseStandard(standard1Id);
        }
    }

    @Test
    void addCourseStandard() {
        int result = courseStandardService.addCourseStandard(standard1);
        assertTrue(result > 0);
        standard1Id = standard1.getId();
        assertNotNull(standard1Id, "ID should be generated after insertion");
    }

    @Test
    void removeCourseStandard() {
        addCourseStandard(); // Ensure standard1 is inserted
        int result = courseStandardService.removeCourseStandard(standard1Id);
        assertTrue(result > 0);
    }

    @Test
    void updateCourseStandard() {
        addCourseStandard(); // Ensure standard1 is inserted
        assertNotNull(standard1Id, "ID should be generated after insertion");
        standard1.setId(standard1Id); // Ensure the ID is set before updating
        standard1.setTitle("Updated Standard 1");
        int result = courseStandardService.updateCourseStandard(standard1);
        assertTrue(result > 0);
        CourseStandard updatedStandard = courseStandardService.getCourseStandardById(standard1Id);
        assertEquals("Updated Standard 1", updatedStandard.getTitle());
    }

    @Test
    void getCourseStandardById() {
        addCourseStandard(); // Ensure standard1 is inserted
        CourseStandard fetchedStandard = courseStandardService.getCourseStandardById(standard1Id);
        assertEquals("Standard 1", fetchedStandard.getTitle());
    }

    @Test
    void getAllCourseStandards() {
        addCourseStandard(); // Ensure standard1 is inserted
        CourseStandard standard2 = new CourseStandard();
        standard2.setTitle("Standard 2");
        standard2.setDescription("Description for Standard 2");
        courseStandardService.addCourseStandard(standard2);
        List<CourseStandard> standards = courseStandardService.getAllCourseStandards();
        assertTrue(standards.size() >= 2);
        boolean foundStandard1 = standards.stream()
                .anyMatch(s -> s.getTitle().equals("Standard 1"));
        boolean foundStandard2 = standards.stream()
                .anyMatch(s -> s.getTitle().equals("Standard 2"));
        assertTrue(foundStandard1 && foundStandard2);
    }
}
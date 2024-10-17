package com.example.service.course;

import com.example.model.course.KnowledgePoint;
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
class KnowledgePointServiceImplTest {

    @Autowired
    private KnowledgePointService knowledgePointService;

    private KnowledgePoint point1;
    private Long point1Id;

    @BeforeEach
    void setUp() {
        point1 = new KnowledgePoint();
        point1.setName("Test Point 1");
        point1.setDescription("Description for Test Point 1");
        point1.setCourseStandardId(1L); // Assume there is a CourseStandard with ID 1
    }

    @AfterEach
    void tearDown() {
        if (point1Id != null) {
            knowledgePointService.removeKnowledgePoint(point1Id);
        }
    }

    @Test
    void addKnowledgePoint() {
        int result = knowledgePointService.addKnowledgePoint(point1);
        assertTrue(result > 0);
        point1Id = point1.getId();
        assertNotNull(point1Id, "ID should be generated after insertion");
    }

    @Test
    void removeKnowledgePoint() {
        addKnowledgePoint(); // Ensure point1 is inserted
        int result = knowledgePointService.removeKnowledgePoint(point1Id);
        assertTrue(result > 0);
    }

    @Test
    void updateKnowledgePoint() {
        addKnowledgePoint(); // Ensure point1 is inserted
        assertNotNull(point1Id, "ID should be generated after insertion");
        point1.setId(point1Id); // Ensure the ID is set before updating
        point1.setName("Updated Test Point 1");
        int result = knowledgePointService.updateKnowledgePoint(point1);
        assertTrue(result > 0);
        KnowledgePoint updatedPoint = knowledgePointService.getKnowledgePointById(point1Id);
        assertEquals("Updated Test Point 1", updatedPoint.getName());
    }

    @Test
    void getKnowledgePointById() {
        addKnowledgePoint(); // Ensure point1 is inserted
        KnowledgePoint fetchedPoint = knowledgePointService.getKnowledgePointById(point1Id);
        assertEquals("Test Point 1", fetchedPoint.getName());
    }

    @Test
    void getAllKnowledgePoints() {
        addKnowledgePoint(); // Ensure point1 is inserted
        KnowledgePoint point2 = new KnowledgePoint();
        point2.setName("Test Point 2");
        point2.setDescription("Description for Test Point 2");
        point2.setCourseStandardId(1L);
        knowledgePointService.addKnowledgePoint(point2);
        List<KnowledgePoint> points = knowledgePointService.getAllKnowledgePoints();
        assertTrue(points.size() >= 2);
        boolean foundPoint1 = points.stream()
                .anyMatch(p -> p.getName().equals("Test Point 1"));
        boolean foundPoint2 = points.stream()
                .anyMatch(p -> p.getName().equals("Test Point 2"));
        assertTrue(foundPoint1 && foundPoint2);
    }
}
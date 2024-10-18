package com.example.Service.user;


import com.example.model.user.School;
import com.example.service.user.SchoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SchoolServiceTest {

    private final SchoolService schoolService;

    @Autowired
    public SchoolServiceTest(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @Test
    void contextLoads() {
        // 检查上下文是否正确加载
        assertNotNull(schoolService, "SchoolService should not be null");
    }

    @Test
    void testAddSchool() {
        // 测试添加学生功能
        School school = new School();
        school.setId(1L);
        school.setName("school1");

        int result = schoolService.addSchool(school);
        assertEquals(1, result, "The student should be added successfully");
        assertNotNull(school.getId(), "The student ID should be set after insertion");
    }

    @Test
    void testGetSchoolById() {
        // 添加后获取 ID
        School school = new School();
        school.setId(2L);
        school.setName("school2");
        schoolService.addSchool(school);

        School fetchedSchool = schoolService.getSchoolById(school.getId());
        assertNotNull(fetchedSchool, "Fetched school should not be null");
        assertEquals("school2", fetchedSchool.getName(), "school name should match");
    }

    @Test
    void testRemoveSchool() {
        // 添加后获取 ID 后删除
        School school = new School();
        school.setId(3L);
        school.setName("school3");
        schoolService.addSchool(school);

        int result = schoolService.removeSchool(school.getId());
        assertEquals(1, result, "The school should be removed successfully");

        School fetchedSchool = schoolService.getSchoolById(school.getId());
        assertNull(fetchedSchool, "The school should be null after being removed");
    }

    @Test
    void testUpdateSchool() {
        // 添加后获取 ID 后更新
        School school = new School();
        school.setId(4L);
        school.setName("school4");
        schoolService.addSchool(school);

        // 更新学校信息
        school.setName("school4-1");
        int result = schoolService.updateSchool(school);
        assertEquals(1, result, "The school should be updated successfully");

        School updateSchool = schoolService.getSchoolById(school.getId());
        assertNotNull(updateSchool, "Updated school should not be null");
        assertEquals("school4-1", updateSchool.getName(), "school name should be updated");
    }

    @Test
    void testGetAllSchools() {
        // 测试获取所有学校
        School school1 = new School();
        school1.setId(5L);
        school1.setName("school5");
        schoolService.addSchool(school1);

        School school2 = new School();
        school2.setId(6L);
        school2.setName("school6");
        schoolService.addSchool(school2);

        List<School> schools = schoolService.getAllSchools();
        assertNotNull(schools, "The list of schools should not be null");
        assertTrue(schools.stream().anyMatch(s -> s.getName().equals("school5")), "The list should contain Test School 5");
        assertTrue(schools.stream().anyMatch(s -> s.getName().equals("school6")), "The list should contain Test School 6");
    }
}

package com.example.service.user;

import com.example.model.user.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TeacherServiceTest {
    private final TeacherService teacherService;
    @Autowired
    public TeacherServiceTest(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @Test
    void testAddTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Test Teacher 1");
        teacher.setEmail("testteacher1@example.com");
        teacher.setPassword("password");
        teacher.setPhoneNumber("1234567890");
        teacher.setSchoolId(1L);

        int result = teacherService.addTeacher(teacher);
        assertEquals(1, result, "The teacher should be added successfully");
        assertNotNull(teacher.getId(), "The teacher ID should be set after insertion");
    }

    @Test
    void testGetTeacherById() {
        Teacher teacher = new Teacher();
        teacher.setName("Test Teacher 2");
        teacher.setEmail("testteacher2@example.com");
        teacher.setPassword("password");
        teacher.setPhoneNumber("1234567890");
        teacher.setSchoolId(1L);
        teacherService.addTeacher(teacher);

        Teacher fetchedTeacher = teacherService.getTeacherById(teacher.getId());
        assertNotNull(fetchedTeacher, "Fetched teacher should not be null");
        assertEquals("Test Teacher 2", fetchedTeacher.getName(), "Teacher name should match");
    }

    @Test
    void testRemoveTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Test Teacher 3");
        teacher.setEmail("testteacher3@example.com");
        teacher.setPassword("password");
        teacher.setPhoneNumber("1234567890");
        teacher.setSchoolId(1L);
        teacherService.addTeacher(teacher);

        int result = teacherService.removeTeacher(teacher.getId());
        assertEquals(1, result, "The teacher should be removed successfully");

        Teacher fetchedTeacher = teacherService.getTeacherById(teacher.getId());
        assertNull(fetchedTeacher, "The teacher should be null after being removed");
    }

    @Test
    void testUpdateTeacher() {
        Teacher teacher = new Teacher();
        teacher.setName("Test Teacher 4");
        teacher.setEmail("testteacher4@example.com");
        teacher.setPassword("password");
        teacher.setPhoneNumber("1234567890");
        teacher.setSchoolId(1L);
        teacherService.addTeacher(teacher);

        teacher.setName("Updated Teacher 4");
        int result = teacherService.updateTeacher(teacher);
        assertEquals(1, result, "The teacher should be updated successfully");

        Teacher updatedTeacher = teacherService.getTeacherById(teacher.getId());
        assertNotNull(updatedTeacher, "Updated teacher should not be null");
        assertEquals("Updated Teacher 4", updatedTeacher.getName(), "Teacher name should be updated");
    }

    @Test
    void testGetAllTeachers() {
        Teacher teacher1 = new Teacher();
        teacher1.setName("Test Teacher 5");
        teacher1.setEmail("testteacher5@example.com");
        teacher1.setPassword("password1");
        teacher1.setPhoneNumber("1234567890");
        teacher1.setSchoolId(1L);
        teacherService.addTeacher(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setName("Test Teacher 6");
        teacher2.setEmail("testteacher6@example.com");
        teacher2.setPassword("password2");
        teacher2.setPhoneNumber("0987654321");
        teacher2.setSchoolId(2L);
        teacherService.addTeacher(teacher2);

        List<Teacher> teachers = teacherService.getAllTeachers();
        assertNotNull(teachers, "The list of teachers should not be null");
        assertTrue(teachers.stream().anyMatch(t -> t.getEmail().equals("testteacher5@example.com")), "The list should contain Test Teacher 5");
        assertTrue(teachers.stream().anyMatch(t -> t.getEmail().equals("testteacher6@example.com")), "The list should contain Test Teacher 6");
    }

}

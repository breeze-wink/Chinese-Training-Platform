package com.example.Service;

import com.example.service.user.StudentService;
import com.example.model.user.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ChineseTrainingPlatformApplicationTests {

    @Autowired
    private StudentService studentService;

    @Test
    void contextLoads() {
        // 检查上下文是否正确加载
        assertNotNull(studentService, "StudentService should not be null");
    }

    @Test
    void testAddStudent() {
        // 测试添加学生功能
        Student student = new Student();
        student.setUsername("testuser1");
        student.setEmail("testuser1@example.com");
        student.setPassword("password");
        student.setName("Test Student 1");
        student.setGrade(3);
        student.setSchoolId(1L);

        int result = studentService.addStudent(student);
        assertEquals(1, result, "The student should be added successfully");
    }

    @Test
    void testGetStudentById() {
        // 通过添加学生，然后获取所有学生以获取其 ID
        Student student = new Student();
        student.setUsername("testuser2");
        student.setEmail("testuser2@example.com");
        student.setPassword("password");
        student.setName("Test Student 2");
        student.setGrade(3);
        student.setSchoolId(1L);
        studentService.addStudent(student);

        List<Student> students = studentService.getAllStudents();
        Long studentId = students.stream()
                .filter(s -> "testuser2".equals(s.getUsername()))
                .findFirst()
                .map(Student::getId)
                .orElse(null);

        assertNotNull(studentId, "Student ID should not be null");

        Student fetchedStudent = studentService.getStudentById(studentId);
        assertNotNull(fetchedStudent, "Fetched student should not be null");
        assertEquals("Test Student 2", fetchedStudent.getName(), "Student name should match");
    }

    @Test
    void testRemoveStudent() {
        // 通过添加学生，然后获取所有学生以获取其 ID
        Student student = new Student();
        student.setUsername("testuser3");
        student.setEmail("testuser3@example.com");
        student.setPassword("password");
        student.setName("Test Student 3");
        student.setGrade(3);
        student.setSchoolId(1L);
        studentService.addStudent(student);

        List<Student> students = studentService.getAllStudents();
        Long studentId = students.stream()
                .filter(s -> "testuser3".equals(s.getUsername()))
                .findFirst()
                .map(Student::getId)
                .orElse(null);

        assertNotNull(studentId, "Student ID should not be null");

        int result = studentService.removeStudent(studentId);
        assertEquals(1, result, "The student should be removed successfully");

        Student fetchedStudent = studentService.getStudentById(studentId);
        assertNull(fetchedStudent, "The student should be null after being removed");
    }

    @Test
    void testUpdateStudent() {
        // 通过添加学生，然后获取所有学生以获取其 ID
        Student student = new Student();
        student.setUsername("testuser4");
        student.setEmail("testuser4@example.com");
        student.setPassword("password");
        student.setName("Test Student 4");
        student.setGrade(3);
        student.setSchoolId(1L);
        studentService.addStudent(student);

        List<Student> students = studentService.getAllStudents();
        Long studentId = students.stream()
                .filter(s -> "testuser4".equals(s.getUsername()))
                .findFirst()
                .map(Student::getId)
                .orElse(null);

        assertNotNull(studentId, "Student ID should not be null");

        // 更新学生信息
        Student insertedStudent = studentService.getStudentById(studentId);
        insertedStudent.setName("Updated Student 4");
        int result = studentService.updateStudent(insertedStudent);
        assertEquals(1, result, "The student should be updated successfully");

        Student updatedStudent = studentService.getStudentById(studentId);
        assertNotNull(updatedStudent, "Updated student should not be null");
        assertEquals("Updated Student 4", updatedStudent.getName(), "Student name should be updated");
    }

    @Test
    void testGetAllStudents() {
        // 测试获取所有学生
        Student student1 = new Student();
        student1.setUsername("testuser5");
        student1.setEmail("testuser5@example.com");
        student1.setPassword("password1");
        student1.setName("Test Student 5");
        student1.setGrade(3);
        student1.setSchoolId(1L);
        studentService.addStudent(student1);

        Student student2 = new Student();
        student2.setUsername("testuser6");
        student2.setEmail("testuser6@example.com");
        student2.setPassword("password2");
        student2.setName("Test Student 6");
        student2.setGrade(4);
        student2.setSchoolId(2L);
        studentService.addStudent(student2);

        List<Student> students = studentService.getAllStudents();
        assertNotNull(students, "The list of students should not be null");
        assertEquals(2, students.size(), "The list should contain two students");
    }
}
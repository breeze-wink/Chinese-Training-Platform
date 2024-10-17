package com.example.service.classes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
//@Transactional
class ClassServiceTest {

  private final ClassService classService;
  @Autowired
  public ClassServiceTest(ClassService classService) {this.classService = classService;}


  @Test
  void testCreateClass() {
    assertNotNull(classService, "ClassService should not be null");
    int result = classService.createClass("className", "classDescription", 1L);
    assertEquals(1, result, "The class should be created successfully");
  }
  @Test
  void testRemoveClass() {
    assertNotNull(classService, "ClassService should not be null");
    int result = classService.removeClass(1L);
    assertEquals(1, result, "The class should be removed successfully");
  }
  @Test
  void testJoinClass() {
    assertNotNull(classService, "ClassService should not be null");
    int result = classService.joinClass("zxcvb", 1L);
    assertEquals(1, result, "The student should be added to the class successfully");
  }

  @Test
  void testRemoveStudent() {
    assertNotNull(classService, "ClassService should not be null");
    int result = classService.removeStudent(1L, 1L);
    assertEquals(1, result, "The student should be removed from the class successfully");
  }
}
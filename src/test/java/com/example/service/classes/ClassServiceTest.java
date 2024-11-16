package com.example.service.classes;

import com.example.model.classes.GroupStudent;
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
  private final ClassGroupService classGroupService;
  @Autowired
  public ClassServiceTest(ClassService classService, ClassGroupService classGroupService) {
    this.classService = classService;
    this.classGroupService = classGroupService;
  }



  @Test
  void testJoinClass() {
    assertNotNull(classService, "ClassService should not be null");
    int result = classService.joinClass("vs7qv", 82L);
    assertEquals(1, result, "The student should be added to the class successfully");
  }

  @Test
  void testRemoveStudent() {
    assertNotNull(classService, "ClassService should not be null");
    int result = classService.removeStudentFromClass(14L, 1L);
    assertEquals(1, result, "The student should be removed from the class successfully");
  }

  @Test
  void testCreateGroup() {
    assertNotNull(classGroupService, "ClassGroupService should not be null");
    int result = classGroupService.createGroup(14L, "groupName1", "groupDescription");
    assertEquals(1, result, "The group should be created successfully");
  }

  @Test
  void testAddStudentToGroup() {
    assertNotNull(classGroupService, "ClassGroupService should not be null");
    int result = classGroupService.addStudentToGroup(4L, 82L);
    assertEquals(1, result, "The student should be added to the group successfully");
  }

  @Test
  void testRemoveStudentFromGroup() {
    assertNotNull(classGroupService, "ClassGroupService should not be null");
    int result = classGroupService.removeStudentFromGroup(1L, 82L);
    assertEquals(1, result, "The student should be removed from the group successfully");
  }

  @Test
  void testRemoveGroup() {
    assertNotNull(classGroupService, "ClassGroupService should not be null");
    int result = classGroupService.removeGroup(2L);
    assertEquals(1, result, "The group should be removed successfully");
  }

  @Test
  void testRemoveClass() {
    assertNotNull(classService, "ClassService should not be null");
    int result = classService.removeClass(14L);
    assertEquals(1, result, "The class should be removed successfully");
  }
}
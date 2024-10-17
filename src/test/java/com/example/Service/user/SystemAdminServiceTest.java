package com.example.Service.user;

import com.example.model.user.SchoolAdmin;
import com.example.model.user.Student;
import com.example.model.user.SystemAdmin;
import com.example.service.user.SystemAdminService;
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
public class SystemAdminServiceTest {

    private final SystemAdminService systemAdminService;
    @Autowired
    public SystemAdminServiceTest(SystemAdminService systemAdminService) {
        this.systemAdminService = systemAdminService;
    }
    @Test
    void contextLoads() {
        // 检查StudentService 是否被正确地加载和实例化
        assertNotNull(systemAdminService, "SchoolAdminService should not be null");
    }
    @Test
    void testAddSchoolAdmin() {

        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setUsername("testuser1");
        systemAdmin.setEmail("testuser1@example.com");
        systemAdmin.setPassword("password");


        int result = systemAdminService.addSystemAdmin(systemAdmin);
        assertEquals(1, result, "The system-admin should be added successfully");
        assertNotNull(systemAdmin.getId(), "The system-admin ID should be set after insertion");

    }
    @Test
    void testRemoveSchoolAdmin() {
        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setUsername("testUser5");
        systemAdmin.setPassword("testPassword5");
        systemAdmin.setEmail("test5@example.com");
        systemAdminService.addSystemAdmin(systemAdmin);

        int result = systemAdminService.removeSystemAdmin(systemAdmin.getId());
        assertEquals(1, result, "The systemAdmin should be removed successfully");

        SystemAdmin fetchedSystemAdmin = systemAdminService.getSystemAdminById(systemAdmin.getId());
        assertNull(fetchedSystemAdmin, "The systemAdmin should be null after being removed");
    }


    @Test
    void testUpdateSchoolAdmin() {
        // 通过添加管理员，然后获取其 ID
        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setUsername("testUser5");
        systemAdmin.setPassword("testPassword5");
        systemAdmin.setEmail("test5@example.com");
        systemAdminService.addSystemAdmin(systemAdmin);


        // 更新系统管理员信息
        systemAdmin.setUsername("Updated SystemAdmin ");
        int result = systemAdminService.updateSystemAdmin(systemAdmin);
        assertEquals(1, result, "The system-admin should be updated successfully");

        SystemAdmin updatedSystemAdmin = systemAdminService.getSystemAdminById(systemAdmin.getId());

        assertNotNull(updatedSystemAdmin, "Updated student should not be null");
        assertEquals("Updated SystemAdmin ", updatedSystemAdmin.getUsername(), "System-admin name should be updated");

    }
    @Test
    void testGetSchoolAdminById() {
        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setUsername("Test SystemAdin 2");
        systemAdmin.setEmail("testuser1@example.com");
        systemAdmin.setPassword("password");
        systemAdminService.addSystemAdmin(systemAdmin);

        SystemAdmin fetchedSytemAdmin = systemAdminService.getSystemAdminById(systemAdmin.getId());
        assertNotNull(fetchedSytemAdmin, "Fetched SytemAdmin should not be null");
        assertEquals("Test SystemAdin 2", fetchedSytemAdmin.getUsername(), "SytemAdmin name should match");

    }
    @Test
    void testGetAllSchoolAdmins() {


        SystemAdmin systemAdmin5 = new SystemAdmin();
        systemAdmin5.setUsername("testUser5");
        systemAdmin5.setPassword("testPassword5");
        systemAdmin5.setEmail("test5@example.com");
        systemAdminService.addSystemAdmin(systemAdmin5);

        SystemAdmin systemAdmin6 = new SystemAdmin();
        systemAdmin6.setUsername("testUser6");
        systemAdmin6.setPassword("testPassword6");
        systemAdmin6.setEmail("test6@example.com");
        systemAdminService.addSystemAdmin(systemAdmin6);
        // 调用Service的getAllSchoolAdmins方法



        List<SystemAdmin> systemAdmins = systemAdminService.getAllSystemAdmins();
        assertNotNull(systemAdmins, "The list of students should not be null");
        assertTrue(systemAdmins.stream().anyMatch(s -> s.getEmail().equals("test5@example.com")), "The list should contain Test SystemAdmin 5");
        assertTrue(systemAdmins.stream().anyMatch(s -> s.getEmail().equals("test6@example.com")), "The list should contain Test SystemAdmin 6");
    }


}

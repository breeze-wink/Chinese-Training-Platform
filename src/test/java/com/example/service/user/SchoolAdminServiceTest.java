package com.example.service.user;

import com.example.model.user.SchoolAdmin;
import com.example.model.user.Student;
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
public class SchoolAdminServiceTest {

    private final SchoolAdminService schoolAdminService;

    @Autowired
    public SchoolAdminServiceTest(SchoolAdminService schoolAdminService) {
        this.schoolAdminService = schoolAdminService;
    }
    @Test
    void contextLoads() {
        // 检查StudentService 是否被正确地加载和实例化
        assertNotNull(schoolAdminService, "SchoolAdminService should not be null");
    }
    @Test
    void testAddSchoolAdmin() {

        // 创建一个测试数据
        SchoolAdmin schoolAdmin = new SchoolAdmin();
        schoolAdmin.setUsername("testUser2");
        schoolAdmin.setPassword("testPassword2");
        schoolAdmin.setEmail("test20@example.com");
        schoolAdmin.setSchoolId(1L);
        schoolAdminService.addSchoolAdmin(schoolAdmin);

    }
    @Test
    void testRemoveSchoolAdmin() {

        schoolAdminService.removeSchoolAdmin(6L);

    }
    @Test
    void testUpdateSchoolAdmin() {


        // 通过添加学生，然后获取其 ID
        SchoolAdmin schoolAdmin = new SchoolAdmin();
        schoolAdmin.setUsername("testUser5");
        schoolAdmin.setPassword("testPassword5");
        schoolAdmin.setEmail("test5@example.com");
        schoolAdmin.setSchoolId(1L);
        schoolAdminService.addSchoolAdmin(schoolAdmin);


        // 更新学生信息
        schoolAdmin.setUsername("Updated Student ");
        int result = schoolAdminService.updateSchoolAdmin(schoolAdmin);
        assertEquals(1, result, "The student should be updated successfully");

        SchoolAdmin updatedSchoolAdmin = schoolAdminService.getSchoolAdminById(schoolAdmin.getId());
        //assertNotNull(updatedSchoolAdmin.getSchoolId(), "Updated schoolID should not be null");
        assertNotNull(updatedSchoolAdmin, "Updated student should not be null");
        assertEquals("Updated Student ", updatedSchoolAdmin.getUsername(), "Student name should be updated");

    }
    @Test
    void testGetSchoolAdminById() {
        // 通过添加学生，然后获取其 ID

        SchoolAdmin fetchedStudent = schoolAdminService.getSchoolAdminById(22L);
        System.out.println(fetchedStudent.getId());

    }
    @Test
    void testGetAllSchoolAdmins() {
        // 调用Service的getAllSchoolAdmins方法
        List<SchoolAdmin> schoolAdmins = schoolAdminService.getAllSchoolAdmins();
        for (int i = 0; i < schoolAdmins.size(); i++) {
            System.out.println(schoolAdmins.get(i).getId());
            System.out.println(schoolAdmins.get(i).getUsername());
            System.out.println(schoolAdmins.get(i).getEmail());
            System.out.println(schoolAdmins.get(i).getPassword());
            System.out.println(schoolAdmins.get(i).getSchoolId());
            System.out.println();

            
        }
    }


}

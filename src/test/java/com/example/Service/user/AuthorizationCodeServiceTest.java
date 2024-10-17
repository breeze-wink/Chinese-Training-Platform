package com.example.Service.user;

import com.example.model.user.AuthorizationCode;
import com.example.service.user.AuthorizationCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthorizationCodeServiceTest {
    private final AuthorizationCodeService authorizationCodeService;

    @Autowired
    public AuthorizationCodeServiceTest(AuthorizationCodeService authorizationCodeService) {
        this.authorizationCodeService = authorizationCodeService;
    }

    @Test
    void contextLoads() {
        // 检查上下文是否正确加载
        assertNotNull(authorizationCodeService, "AuthorizationCodeService should not be null");
    }

    @Test
    void testaddAuthorizationCode() {
        // 测试添加功能
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setCode("testauthorizationCode1");
        authorizationCode.setSchoolId(1L);
        authorizationCode.setExpirationDate(LocalDateTime.now());
        authorizationCode.setUsageLimit(1);

        int result = authorizationCodeService.addAuthorizationCode(authorizationCode);
        assertEquals(1, result, "The authorizationCode should be added successfully");
        assertNotNull(authorizationCode.getCode(), "The authorizationCode Code should be set after insert");
    }

    @Test
    void testGetAuthorizationCodeByCode() {
        // 添加后获取 Code
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setCode("testauthorizationCode2");
        authorizationCode.setSchoolId(2L);
        authorizationCode.setExpirationDate(LocalDateTime.now());
        authorizationCode.setUsageLimit(2);

        int result = authorizationCodeService.addAuthorizationCode(authorizationCode);
        assertEquals(1, result, "The authorizationCode should be added successfully");

        AuthorizationCode fetchedAuthorizationCode = authorizationCodeService.getAuthorizationCodeByCode(authorizationCode.getCode());
        assertNotNull(fetchedAuthorizationCode, "Fetched authorizationCode should not be null");
        assertNotNull(fetchedAuthorizationCode.getSchoolId(), "Fetched authorizationCode schoolId should not be null");
        assertEquals(2L, fetchedAuthorizationCode.getSchoolId(), "AuthorizationCode schoolId should match");

        // 调试信息输出
        System.out.println("Fetched AuthorizationCode: " + fetchedAuthorizationCode);

    }

    @Test
    void testRemoveAuthorizationCode() {
        // 添加后获取 Code 后删除
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setCode("testauthorizationCode3");
        authorizationCode.setSchoolId(3L);
        authorizationCode.setExpirationDate(LocalDateTime.now());
        authorizationCode.setUsageLimit(3);
        authorizationCodeService.addAuthorizationCode(authorizationCode);

        int result = authorizationCodeService.removeAuthorizationCode(authorizationCode.getCode());
        assertEquals(1, result, "The authorizationCode should be removed successfully");

        AuthorizationCode fetchedAuthorizationCode = authorizationCodeService.getAuthorizationCodeByCode(authorizationCode.getCode());
        assertNull(fetchedAuthorizationCode, "The authorizationCode should be null after being removed");
    }

    @Test
    void testUpdateAuthorizationCode() {
        // 添加后获取 Code 后更新
        AuthorizationCode authorizationCode = new AuthorizationCode();
        authorizationCode.setCode("testauthorizationCode4");
        authorizationCode.setSchoolId(4L);
        authorizationCode.setExpirationDate(LocalDateTime.now());
        authorizationCode.setUsageLimit(4);
        authorizationCodeService.addAuthorizationCode(authorizationCode);

        // 更新授权码
        authorizationCode.setCode("testauthorizationCode4-1");
        int result = authorizationCodeService.updateAuthorizationCode(authorizationCode);
        assertEquals(1, result, "The authorizationCode should be updated successfully");

        AuthorizationCode updatedAuthorizationCode = authorizationCodeService.getAuthorizationCodeByCode(authorizationCode.getCode());
        assertNotNull(updatedAuthorizationCode, "Updated authorizationCode should not be null");
        assertEquals("testauthorizationCode4-1", updatedAuthorizationCode.getCode(), "AuthorizationCode code should be updated");
    }

    @Test
    void testGetAllAuthorizationCodes() {
        // 测试获取所有授权码
        AuthorizationCode authorizationCode1 = new AuthorizationCode();
        authorizationCode1.setCode("testauthorizationCode5");
        authorizationCode1.setSchoolId(5L);
        authorizationCode1.setExpirationDate(LocalDateTime.now());
        authorizationCode1.setUsageLimit(5);
        authorizationCodeService.addAuthorizationCode(authorizationCode1);

        AuthorizationCode authorizationCode2 = new AuthorizationCode();
        authorizationCode2.setCode("testauthorizationCode6");
        authorizationCode2.setSchoolId(6L);
        authorizationCode2.setExpirationDate(LocalDateTime.now());
        authorizationCode2.setUsageLimit(6);
        authorizationCodeService.addAuthorizationCode(authorizationCode2);

        List<AuthorizationCode> authorizationCodes = authorizationCodeService.getAllAuthorizationCodes();
        assertNotNull(authorizationCodes, "The list of authorizationCodes should not be null");
        assertTrue(authorizationCodes.stream().anyMatch(s -> s.getCode().equals("testauthorizationCode5")), "The list should contain Test authorizationCode5");
        assertTrue(authorizationCodes.stream().anyMatch(s -> s.getCode().equals("testauthorizationCode6")), "The list should contain Test authorizationCode6");
    }
}

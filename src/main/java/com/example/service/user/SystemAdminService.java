package com.example.service.user;

import com.example.model.user.SystemAdmin;

import java.util.List;

public interface SystemAdminService {
    int addSystemAdmin(SystemAdmin systemAdmin);
    int removeSystemAdmin(Long id);
    int updateSystemAdmin(SystemAdmin systemAdmin);
    SystemAdmin getSystemAdminById(Long id);
    List<SystemAdmin> getAllSystemAdmins();

    SystemAdmin authenticate(String account, String password);
}
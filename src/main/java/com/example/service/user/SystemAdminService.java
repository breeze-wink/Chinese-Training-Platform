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

    SystemAdmin getSystemAdminByUsername(String username);

    boolean emailExist(String email);
    boolean usernameExist(String username);

    void updatePassword(SystemAdmin systemAdmin);

    SystemAdmin selectByEmail(String email);
    SystemAdmin selectByUsername(String username);
}
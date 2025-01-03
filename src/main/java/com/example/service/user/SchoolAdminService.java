package com.example.service.user;

import com.example.model.user.SchoolAdmin;

import java.util.List;

public interface SchoolAdminService {
    int addSchoolAdmin(SchoolAdmin schoolAdmin);
    int removeSchoolAdmin(Long id);
    int updateSchoolAdmin(SchoolAdmin schoolAdmin);
    SchoolAdmin getSchoolAdminById(Long id);
    List<SchoolAdmin> getAllSchoolAdmins();

    SchoolAdmin authenticate(String account, String password);

    boolean checkExistUsername(String name);

    SchoolAdmin getSchoolAdminByUsername(String username);

    boolean emailExist(String email);

    void updatePassword(SchoolAdmin admin);

    SchoolAdmin selectByEmail(String email);
}

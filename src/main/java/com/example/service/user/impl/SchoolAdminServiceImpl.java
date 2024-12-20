package com.example.service.user.impl;

import com.example.mapper.user.SchoolAdminMapper;
import com.example.model.user.SchoolAdmin;
import com.example.service.user.SchoolAdminService;
import com.example.service.utils.PasswordEncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolAdminServiceImpl implements SchoolAdminService {
    private final SchoolAdminMapper schoolAdminMapper;
    private final PasswordEncodeService passwordEncodeService;
    @Autowired
    public SchoolAdminServiceImpl (SchoolAdminMapper schoolAdminMapper,
                                   PasswordEncodeService passwordEncodeService) {
        this.schoolAdminMapper = schoolAdminMapper;
        this.passwordEncodeService = passwordEncodeService;
    }
    @Override
    @Transactional
    public int addSchoolAdmin(SchoolAdmin schoolAdmin) {
        schoolAdmin.setPassword(passwordEncodeService.encode(schoolAdmin.getPassword()));
        return schoolAdminMapper.insert(schoolAdmin);
    }

    @Override
    @Transactional
    public int removeSchoolAdmin(Long id) {
        return schoolAdminMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateSchoolAdmin(SchoolAdmin schoolAdmin) {
        return schoolAdminMapper.update(schoolAdmin);
    }

    @Override
    @Transactional
    public SchoolAdmin getSchoolAdminById(Long id) {
        if (schoolAdminMapper.selectById(id) == null) {
            return null;
        }
        return schoolAdminMapper.selectById(id);
    }

    @Override
    @Transactional
    public List<SchoolAdmin> getAllSchoolAdmins() {
        return schoolAdminMapper.selectAll();
    }

    @Override
    public SchoolAdmin authenticate(String account, String password) {
        SchoolAdmin schoolAdmin = schoolAdminMapper.findByAccountOrEmail(account);
        if (schoolAdmin != null && passwordEncodeService.matches(password, schoolAdmin.getPassword())) {
            return schoolAdmin;
        }
        return null;
    }

    @Override
    public boolean checkExistUsername(String name) {
        return schoolAdminMapper.selectByUsername(name) != null;
    }

    @Override
    public SchoolAdmin getSchoolAdminByUsername(String username) {
        return schoolAdminMapper.selectByUsername(username);
    }

    @Override
    @Transactional
    public boolean emailExist(String email) {
        SchoolAdmin schoolAdmin = schoolAdminMapper.emailExist(email);
        return schoolAdmin != null;
    }

    @Override
    public void updatePassword(SchoolAdmin admin) {
        admin.setPassword(passwordEncodeService.encode(admin.getPassword()));
        schoolAdminMapper.update(admin);
    }

    @Override
    @Transactional
    public SchoolAdmin selectByEmail(String email) {
        return schoolAdminMapper.selectByEmail(email);
    }
}

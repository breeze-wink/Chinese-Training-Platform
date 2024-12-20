package com.example.service.user.impl;

import com.example.mapper.user.SystemAdminMapper;
import com.example.model.user.SystemAdmin;
import com.example.service.user.SystemAdminService;
import com.example.service.utils.PasswordEncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SystemAdminServiceImpl implements SystemAdminService {
    private final SystemAdminMapper systemAdminMapper;
    private final PasswordEncodeService passwordEncodeService;
    @Autowired
    public SystemAdminServiceImpl(SystemAdminMapper systemAdminMapper,
                                  PasswordEncodeService passwordEncodeService){
        this.systemAdminMapper = systemAdminMapper;
        this.passwordEncodeService = passwordEncodeService;
    }
    @Override
    @Transactional
    public int addSystemAdmin(SystemAdmin systemAdmin) {
        systemAdmin.setPassword(passwordEncodeService.encode(systemAdmin.getPassword()));
        return systemAdminMapper.insert(systemAdmin);
    }

    @Override
    @Transactional
    public int removeSystemAdmin(Long id) {
        return systemAdminMapper.delete(id);
    }

    @Override
    @Transactional
    public int updateSystemAdmin(SystemAdmin systemAdmin) {
        return systemAdminMapper.update(systemAdmin);
    }

    @Override
    @Transactional
    public SystemAdmin getSystemAdminById(Long id) {
        return systemAdminMapper.selectById(id);
    }

    @Override
    @Transactional
    public List<SystemAdmin> getAllSystemAdmins() {
        return systemAdminMapper.selectAll();
    }

    @Override
    public SystemAdmin authenticate(String account, String password) {
        SystemAdmin systemAdmin = systemAdminMapper.findByAccountOrEmail(account);
        if (systemAdmin == null || passwordEncodeService.matches(password, systemAdmin.getPassword()))
            return null;
        return systemAdmin;
    }

    @Override
    public SystemAdmin getSystemAdminByUsername(String username) {
        return systemAdminMapper.findByUsername(username);
    }

    @Override
    @Transactional
    public boolean emailExist(String email) {
        SystemAdmin systemAdmin = systemAdminMapper.emailExist(email);
        return systemAdmin != null;
    }

    @Override
    public void updatePassword(SystemAdmin systemAdmin) {
        systemAdmin.setPassword(passwordEncodeService.encode(systemAdmin.getPassword()));
        systemAdminMapper.update(systemAdmin);
    }


}

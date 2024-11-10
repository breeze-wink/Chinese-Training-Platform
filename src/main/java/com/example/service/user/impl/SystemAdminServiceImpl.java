package com.example.service.user.impl;

import com.example.mapper.user.SystemAdminMapper;
import com.example.model.user.SystemAdmin;
import com.example.service.user.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SystemAdminServiceImpl implements SystemAdminService {
    private final SystemAdminMapper systemAdminMapper;
    @Autowired
    public SystemAdminServiceImpl(SystemAdminMapper systemAdminMapper){
        this.systemAdminMapper = systemAdminMapper;
    }
    @Override
    @Transactional
    public int addSystemAdmin(SystemAdmin systemAdmin) {
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
        if (systemAdmin == null || !systemAdmin.getPassword().equals(password))
            return null;
        return systemAdmin;
    }
}

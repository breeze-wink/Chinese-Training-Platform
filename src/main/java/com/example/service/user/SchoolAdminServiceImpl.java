package com.example.service.user;

import com.example.mapper.user.SchoolAdminMapper;
import com.example.model.user.SchoolAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchoolAdminServiceImpl implements SchoolAdminService{
    private final SchoolAdminMapper schoolAdminMapper;
    @Autowired
    public SchoolAdminServiceImpl (SchoolAdminMapper schoolAdminMapper) {
        this.schoolAdminMapper = schoolAdminMapper;
    }
    @Override
    @Transactional
    public int addSchoolAdmin(SchoolAdmin schoolAdmin) {
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
        return schoolAdminMapper.selectById(id);
    }

    @Override
    @Transactional
    public List<SchoolAdmin> getAllSchoolAdmins() {
        return schoolAdminMapper.selectAll();
    }
}

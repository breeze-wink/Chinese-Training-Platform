package com.example.service.user.impl;

import com.example.mapper.user.AuthorizationCodeMapper;
import com.example.model.user.AuthorizationCode;
import com.example.service.user.AuthorizationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorizationCodeServiceImpl implements AuthorizationCodeService {
    private final AuthorizationCodeMapper authorizationCodeMapper;
    @Autowired
    public AuthorizationCodeServiceImpl (AuthorizationCodeMapper authorizationCodeMapper) {
        this.authorizationCodeMapper = authorizationCodeMapper;
    }

    @Override
    @Transactional
    public int addAuthorizationCode(AuthorizationCode authorizationCode) {
        return authorizationCodeMapper.insert(authorizationCode);
    }

    @Override
    @Transactional
    public int removeAuthorizationCode(Long schoolId) {
        return authorizationCodeMapper.delete(schoolId);
    }

    @Override
    @Transactional
    public int updateAuthorizationCode(AuthorizationCode authorizationCode) {
        return authorizationCodeMapper.update(authorizationCode);
    }

    @Override
    public AuthorizationCode getAuthorizationCodeBySchoolId(Long schoolId) {
        return authorizationCodeMapper.selectBySchoolId(schoolId);
    }

    @Override
    public List<AuthorizationCode> getAllAuthorizationCodes() {
        return authorizationCodeMapper.selectAll();
    }

    @Override
    @Transactional
    public Boolean codeIsExist(String code) {
        return authorizationCodeMapper.selectByCode(code) != null;
    }

    @Override
    @Transactional
    public Boolean schoolIsExist(Long schoolId) {
        return authorizationCodeMapper.selectBySchoolId(schoolId) != null;
    }

    @Override
    public AuthorizationCode getAuthorizationCodeByCode(String code) {
        return authorizationCodeMapper.selectByCode(code);
    }

}

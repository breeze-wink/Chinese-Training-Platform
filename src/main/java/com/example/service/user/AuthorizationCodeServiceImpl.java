package com.example.service.user;

import com.example.mapper.user.AuthorizationCodeMapper;
import com.example.model.user.AuthorizationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AuthorizationCodeServiceImpl implements AuthorizationCodeService{
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
    public int removeAuthorizationCode(String code) {
        return authorizationCodeMapper.delete(code);
    }

    @Override
    @Transactional
    public int updateAuthorizationCode(AuthorizationCode authorizationCode) {
        return authorizationCodeMapper.update(authorizationCode);
    }

    @Override
    public AuthorizationCode getAuthorizationCodeByCode(String code) {
        return authorizationCodeMapper.selectByCode(code);
    }

    @Override
    public List<AuthorizationCode> getAllAuthorizationCodes() {
        return authorizationCodeMapper.selectAll();
    }

}

package com.example.service.user;

import com.example.model.user.AuthorizationCode;

import java.util.List;

public interface AuthorizationCodeService {
    int addAuthorizationCode(AuthorizationCode authorizationCode);
    int removeAuthorizationCode(String code);
    int updateAuthorizationCode(AuthorizationCode authorizationCode);
    AuthorizationCode getAuthorizationCodeById(String code);
    List<AuthorizationCode> getAllAuthorizationCodes();
}

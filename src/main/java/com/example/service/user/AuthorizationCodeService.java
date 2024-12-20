package com.example.service.user;

import com.example.model.user.AuthorizationCode;

import java.util.List;

public interface AuthorizationCodeService {
    int addAuthorizationCode(AuthorizationCode authorizationCode);
    int removeAuthorizationCode(Long schoolId);
    int updateAuthorizationCode(AuthorizationCode authorizationCode);
    AuthorizationCode getAuthorizationCodeBySchoolId(Long schoolId);
    List<AuthorizationCode> getAllAuthorizationCodes();
    Boolean codeIsExist(String code);
    Boolean schoolIsExist(Long schoolId);

    AuthorizationCode getAuthorizationCodeByCode(String code);
}

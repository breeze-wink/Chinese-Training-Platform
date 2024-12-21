package com.example.service.user;

import com.example.model.user.BaseUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    BaseUser loadUserByUsernameAndClassName(Long id, String className);
}
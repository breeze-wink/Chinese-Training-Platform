package com.example.model.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
public abstract class BaseUser implements UserDetails {
    protected Long id;
    protected String username;
    protected String email;
    protected String password;
    // 返回一个空的权限集合，因为系统目前不涉及权限
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 返回空集合
    }
    @Override
    public boolean isAccountNonExpired() {
        return true; // 假设账户没有过期
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 假设账户没有被锁定
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 假设凭证没有过期
    }

    @Override
    public boolean isEnabled() {
        return true; // 假设账户是启用的
    }
}
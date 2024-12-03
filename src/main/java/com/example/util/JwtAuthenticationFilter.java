package com.example.util;

import com.example.model.user.BaseUser;
import com.example.service.user.UserService;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    // 添加构造函数来接收AuthenticationManager
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenUtil jwtTokenUtil,
                                   UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nullable HttpServletResponse response,
                                    @Nullable FilterChain filterChain)
            throws ServletException, IOException {
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Origin, Accept");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            String token = request.getHeader("Authorization");

            if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String username = jwtTokenUtil.getUsernameFromToken(token);
                String className = jwtTokenUtil.getClassNameFromToken(token);

                if (username != null) {
                    BaseUser user = userService.loadUserByUsernameAndClassName(username, className);
                    if (user != null && jwtTokenUtil.validateToken(token)) {
                        Authentication authentication = getAuthentication(user);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (SignatureException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT signature.");
            return;
        }

        if (filterChain != null) {
            filterChain.doFilter(request, response);
        }
    }

    private Authentication getAuthentication(BaseUser user) {
        // 这里可以调用authenticationManager进行额外的认证逻辑（如果需要）
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
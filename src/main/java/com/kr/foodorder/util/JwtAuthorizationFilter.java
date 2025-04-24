package com.kr.foodorder.util;

import com.kr.foodorder.util.JwtTokenUtil;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Collection;

public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthorizationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;  // ServletRequest를 HttpServletRequest로 캐스팅
        String token = jwtTokenUtil.getTokenFromRequest(httpRequest);  // HttpServletRequest를 넘겨서 처리
        if (token != null && jwtTokenUtil.validateToken(token)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);

            // 역할 정보 추출 (예: ROLE_USER, ROLE_ADMIN 등)
            Collection<? extends GrantedAuthority> authorities = jwtTokenUtil.getRolesFromToken(token);

            // 인증 객체 생성 및 SecurityContext에 설정
            JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(username, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
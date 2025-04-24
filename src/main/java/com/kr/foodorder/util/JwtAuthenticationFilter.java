package com.kr.foodorder.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 요청에서 JWT 토큰을 가져오기
        String token = jwtTokenProvider.getTokenFromRequest(request);

        // 토큰이 존재하고 유효한 경우, 인증 정보를 SecurityContext에 설정
        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsername(token);
            // 인증된 사용자 정보를 SecurityContext에 설정
            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(username));
        }

        // 다음 필터로 요청을 전달
        chain.doFilter(request, response);
    }
}
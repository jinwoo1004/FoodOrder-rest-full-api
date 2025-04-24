package com.kr.foodorder.util;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * JWT 기반 인증 객체
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;

    public JwtAuthenticationToken(String principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // 권한이 있는 경우 인증 완료로 간주
    }

    @Override
    public Object getCredentials() {
        return null; // 비밀번호 등의 자격증명은 JWT 토큰 기반 인증에선 사용되지 않음
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
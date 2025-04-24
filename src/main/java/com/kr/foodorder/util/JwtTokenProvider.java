package com.kr.foodorder.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "your-secret-key";
    
    // 인증 객체 생성
    public UsernamePasswordAuthenticationToken getAuthentication(String username) {
        // 실제 UserDetails를 로드하는 코드가 필요 (예: UserService에서 로드)
        UserDetails userDetails = loadUserByUsername(username);

        // 권한을 가져오기 (여기서는 기본적으로 ROLE_USER 권한을 가정)
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_USER")
        );

        // 인증 객체 생성
        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    // 사용자 이름을 기준으로 UserDetails를 로드하는 예시 메서드 (이 예시는 UserService에서 구현되어야 함)
    private UserDetails loadUserByUsername(String username) {
        // 실제 DB나 UserService를 통해 User를 가져오는 코드 필요
        // 예시로 임시 User 생성
        return new User(username, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
    
    public String getTokenFromRequest(HttpServletRequest request) {
        // Authorization 헤더에서 "Bearer " 부분을 잘라내어 토큰을 가져옴
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 부분을 제외하고 토큰 반환
        }
        return null;
    }
    
    @SuppressWarnings("deprecation")
	public boolean validateToken(String token) {
        try {
            // 토큰의 서명을 확인하고, 토큰이 유효한지 검증
            Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws(token);
            return true;  // 서명 검증을 통과하면 유효한 토큰
        } catch (Exception e) {
            return false;  // 서명 검증 실패 시 유효하지 않은 토큰
        }
    }
    
    public String getUsername(String token) {
        @SuppressWarnings("deprecation")
		Claims claims = Jwts.parser()
                .setSigningKey("yourSecretKey")  // 비밀 키로 서명된 토큰을 파싱
                .parseClaimsJws(token)
                .getBody();  // 토큰에서 클레임을 추출
        return claims.getSubject();  // 클레임에서 사용자 이름(혹은 이메일) 반환
    }
    
    @SuppressWarnings("deprecation")
	public String generateToken(Authentication authentication) {
        // token 생성 로직 (예시)
        return Jwts.builder()
                   .setSubject(authentication.getName())
                   .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                   .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                   .compact();
    }
}
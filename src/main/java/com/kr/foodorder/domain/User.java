package com.kr.foodorder.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails { // UserDetails 인터페이스 구현
	private String username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email; // username으로 사용될 email 필드
    private String password;
    private String name;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>(); // 유저의 역할들을 저장하는 Set

    // UserDetails의 getUsername() 메서드 구현
    @Override
    public String getUsername() {
        return email; // email을 username으로 사용
    }

    // UserDetails의 getAuthorities() 메서드 구현
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())) // ROLE_ 접두어 붙여서 반환
                .collect(Collectors.toSet());
    }

    // UserDetails의 isAccountNonExpired() 메서드 구현
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료되지 않음
    }

    // UserDetails의 isAccountNonLocked() 메서드 구현
    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금되지 않음
    }

    // UserDetails의 isCredentialsNonExpired() 메서드 구현
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료되지 않음
    }

    // UserDetails의 isEnabled() 메서드 구현
    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화됨
    }
}
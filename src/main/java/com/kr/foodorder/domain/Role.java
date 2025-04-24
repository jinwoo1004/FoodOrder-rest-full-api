package com.kr.foodorder.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // 예시로 권한 이름을 저장할 수 있음

    @Override
    public String toString() {
        return name;
    }
}
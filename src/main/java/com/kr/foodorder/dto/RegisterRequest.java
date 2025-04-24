package com.kr.foodorder.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // "USER" or "ADMIN"
    private String email;
}
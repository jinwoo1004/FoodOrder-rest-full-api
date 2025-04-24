package com.kr.foodorder.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAITING("주문 대기"),
    PAID("결제 완료"),
    CANCELLED("주문 취소"),
    COMPLETED("주문 완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
package com.kr.foodorder.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private Long foodId;  // foodId
    private Integer quantity;  // 수량
    private Double price;  // 가격 등

    // 다른 필요한 필드를 추가
}
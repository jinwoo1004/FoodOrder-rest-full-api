package com.kr.foodorder.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.kr.foodorder.domain.Item;

@Getter
@Setter
public class OrderRequest {
    private List<Item> items;  // foodIds 대신 Item 객체 리스트로 변경
    private Long userId;
}
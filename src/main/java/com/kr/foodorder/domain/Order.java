package com.kr.foodorder.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders") // 'order'가 예약어라서
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private com.kr.foodorder.enums.OrderStatus status;

    // 총 금액을 계산하는 메서드 추가
    public double getTotalAmount() {
        return orderItems.stream()
                .mapToDouble(item -> item.getFood().getPrice() * item.getQuantity()) // food의 가격과 수량을 곱하여 합산
                .sum();
    }
}
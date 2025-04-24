package com.kr.foodorder.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    private String method;
    private double amount;

    @Enumerated(EnumType.STRING)
    private com.kr.foodorder.enums.PaymentStatus status;
}
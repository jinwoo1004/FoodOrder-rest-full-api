package com.kr.foodorder.repository;

import com.kr.foodorder.domain.Order;
import com.kr.foodorder.domain.User;
import com.kr.foodorder.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByStatus(OrderStatus status);
}
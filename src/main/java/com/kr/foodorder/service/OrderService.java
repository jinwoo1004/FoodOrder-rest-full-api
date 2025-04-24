package com.kr.foodorder.service;

import com.kr.foodorder.domain.*;
import com.kr.foodorder.enums.OrderStatus;
import com.kr.foodorder.repository.OrderItemRepository;
import com.kr.foodorder.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final FoodService foodService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, FoodService foodService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.foodService = foodService;
    }

    @Transactional
    public Order createOrder(User user, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.WAITING);
        order.setOrderItems(orderItems);
        for (OrderItem item : orderItems) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }
        return orderRepository.save(order);
    }

    public List<Order> getOrders(User user) {
        return orderRepository.findByUser(user);
    }

    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
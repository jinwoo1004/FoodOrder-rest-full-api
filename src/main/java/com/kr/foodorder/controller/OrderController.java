package com.kr.foodorder.controller;

import com.kr.foodorder.domain.Order;
import com.kr.foodorder.domain.OrderItem;
import com.kr.foodorder.domain.User;
import com.kr.foodorder.dto.OrderRequest;
import com.kr.foodorder.enums.OrderStatus;
import com.kr.foodorder.repository.UserRepository;
import com.kr.foodorder.service.OrderService;
import com.kr.foodorder.service.PaymentService;
import com.kr.foodorder.service.StripePaymentService;
import com.kr.foodorder.service.KakaoPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final StripePaymentService stripePaymentService;
    private final KakaoPayService kakaoPayService;

    public OrderController(OrderService orderService, UserRepository userRepository, PaymentService paymentService,
                            StripePaymentService stripePaymentService, KakaoPayService kakaoPayService) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.stripePaymentService = stripePaymentService;
        this.kakaoPayService = kakaoPayService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestBody OrderRequest orderRequest) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(dto -> {
            OrderItem item = new OrderItem();
            item.setId(dto.getFoodId());
            item.setQuantity(dto.getQuantity());
            return item;
        }).collect(Collectors.toList());

        Order order = orderService.createOrder(user, orderItems);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return ResponseEntity.ok(orderService.getOrders(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderService.getOrder(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("삭제 완료");
    }

    @PostMapping("/{id}/pay/stripe")
    public ResponseEntity<?> payWithStripe(@PathVariable Long id, @RequestParam String token) {
        Order order = orderService.getOrder(id)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        boolean paid = stripePaymentService.pay(order.getTotalAmount(), token);
        if (paid) {
            paymentService.createPayment(order, "STRIPE", order.getTotalAmount());
            return ResponseEntity.ok("Stripe 결제 완료");
        } else {
            return ResponseEntity.badRequest().body("결제 실패");
        }
    }

    @PostMapping("/{id}/pay/kakao")
    public ResponseEntity<?> payWithKakao(@PathVariable Long id, @RequestParam String kakaoToken) {
        Order order = orderService.getOrder(id)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        boolean paid = kakaoPayService.pay(order.getTotalAmount(), kakaoToken);
        if (paid) {
            paymentService.createPayment(order, "KAKAO", order.getTotalAmount());
            return ResponseEntity.ok("카카오페이 결제 완료");
        } else {
            return ResponseEntity.badRequest().body("결제 실패");
        }
    }
}
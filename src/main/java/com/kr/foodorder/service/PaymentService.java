package com.kr.foodorder.service;

import com.kr.foodorder.domain.Order;
import com.kr.foodorder.domain.Payment;
import com.kr.foodorder.enums.PaymentStatus;
import com.kr.foodorder.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Transactional
    public Payment createPayment(Order order, String paymentMethod, double d) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(d);
        payment.setMethod(paymentMethod);
        payment.setStatus(PaymentStatus.PENDING);
        return paymentRepository.save(payment);
    }

    public Payment completePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("결제 내역을 찾을 수 없습니다."));
        payment.setStatus(PaymentStatus.COMPLETED);
        return paymentRepository.save(payment);
    }

    public Payment failPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("결제 내역을 찾을 수 없습니다."));
        payment.setStatus(PaymentStatus.FAILED);
        return paymentRepository.save(payment);
    }
}
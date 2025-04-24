package com.kr.foodorder.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoPayService {

    public KakaoPayService() {
        Stripe.apiKey = "kakaopay_test_1234567890abcdef"; // 여기에 네 Stripe 비밀 키 입력해!
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency) throws Exception {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // Stripe는 금액을 '센트' 단위로 넣어야 해 (ex: 1000 == 10.00달러)
                .setCurrency(currency)
                .build();

        return PaymentIntent.create(params);
    }

    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws Exception {
        return PaymentIntent.retrieve(paymentIntentId);
    }
    
    public boolean pay(double d, String token) {
        // 실제 stripe 연동 로직은 여기에
        System.out.println("Kakao pay 연동 결제 시뮬레이션: " + d + "원 결제 진행");
        return true;
    }
}
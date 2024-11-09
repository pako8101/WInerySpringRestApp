package com.kamenov.wineryspringrestapp.service;

public interface PaymentService {
    boolean processPayment(Long orderId);
}

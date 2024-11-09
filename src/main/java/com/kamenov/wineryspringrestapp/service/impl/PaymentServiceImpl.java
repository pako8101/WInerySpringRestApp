package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.CartItem;
import com.kamenov.wineryspringrestapp.models.entity.Order;
import com.kamenov.wineryspringrestapp.repository.OrderRepository;
import com.kamenov.wineryspringrestapp.service.CartService;
import com.kamenov.wineryspringrestapp.service.OrderService;
import com.kamenov.wineryspringrestapp.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

private final OrderService orderService;

private final CartService cartService;
private final OrderRepository orderRepository;

    public PaymentServiceImpl(OrderService orderService,
                              CartService cartService, OrderRepository orderRepository) {
        this.orderService = orderService;

        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }


    public boolean processPayment(Long orderId) {
        Order order = orderService.findById(orderId);
        List<CartItem> items = cartService.getCartItems();

        // Проверка на наличността на всеки артикул в количката
        for (CartItem cartItem : items) {
            if (cartItem.getQuantity() > cartItem.getCart().getItems().size()) {
                return false; // Ако няма достатъчна наличност, отказваме плащането
            }
        }

        // Фиктивна логика за плащане (тук можеш да добавиш истинска логика за интеграция)
        // Примерно, можем да добавим API за плащане или друг механизъм
        boolean isPaymentSuccessful = true; // Симулация на успешна транзакция

        if (isPaymentSuccessful) {
            // Ако плащането е успешно, намаляваме наличността на артикулите
            for (CartItem cartItem : items) {
                int newStock = cartItem.getQuantity()- cartItem.getCart().getItems().size();
                cartItem.setQuantity(newStock);
            }
            //order.setStatus("Завършена");
            orderRepository.save(order);
        }

        return isPaymentSuccessful;
    }

}

package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.NoSuchOrderFound;
import com.kamenov.wineryspringrestapp.models.entity.*;
import com.kamenov.wineryspringrestapp.repository.OrderRepository;
import com.kamenov.wineryspringrestapp.repository.ShoppingCartRepository;
import com.kamenov.wineryspringrestapp.repository.UserRepository;
import com.kamenov.wineryspringrestapp.service.CartService;
import com.kamenov.wineryspringrestapp.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
private final UserRepository userRepository;
    public OrderServiceImpl(CartService cartService, ShoppingCartRepository shoppingCartRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.cartService = cartService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order checkOut(UserEntity user) {
        ShoppingCart cart = shoppingCartRepository.findByUserEntityAndCompletedFalse(user)
                .orElseThrow(() -> new RuntimeException("No active cart found"));


        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        for (CartItem item : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setWine(item.getWine());
            orderItem.setQuantity(item.getQuantity());
            order.getOrders().add(orderItem);
        }

        cart.setCompleted(true);
        shoppingCartRepository.save(cart);
        if (order.getUser().getId() == user.getId()) {
            userRepository.save(order.getUser());
        }
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NoSuchOrderFound("No order found"));
    }
}


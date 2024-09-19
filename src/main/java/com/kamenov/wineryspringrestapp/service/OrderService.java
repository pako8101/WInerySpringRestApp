package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.Order;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;

public interface OrderService {


    public Order checkOut(UserEntity user);

    Order findById(Long orderId);
}

package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "order")
public class Order extends BaseEntity{

@OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem>orders = new ArrayList<>();
@ManyToOne
private UserEntity user;

private LocalDateTime orderDate;

    public Order() {
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public Order setOrders(List<OrderItem> orders) {
        this.orders = orders;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public Order setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }
}

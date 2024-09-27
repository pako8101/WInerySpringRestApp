package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

@OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orders = new ArrayList<>();
@ManyToOne
private UserEntity user;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

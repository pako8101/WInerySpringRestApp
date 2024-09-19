package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @ManyToOne
    private WineEntity wine;

    private int quantity;

    @ManyToOne
    private Order order;

    public OrderItem() {}

    public WineEntity getWine() {
        return wine;
    }

    public OrderItem setWine(WineEntity wine) {
        this.wine = wine;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public OrderItem setOrder(Order order) {
        this.order = order;
        return this;
    }
}

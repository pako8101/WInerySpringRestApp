package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItem extends BaseEntity {
    @ManyToOne
    private WineEntity wine;


    private int quantity;
    @ManyToOne
    private ShoppingCart cart;
    public CartItem() {}

    public WineEntity getWine() {
        return wine;
    }

    public CartItem setWine(WineEntity wine) {
        this.wine = wine;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public CartItem setCart(ShoppingCart cart) {
        this.cart = cart;
        return this;
    }
    public double  getSubtotal(){

        return quantity * wine.getPrice();
    }
}

package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {

    @OneToMany
    private List<CartItem> items = new ArrayList<>();
@OneToOne
    private UserEntity userEntity;

private boolean completed = false;

    public ShoppingCart() {
    }

    public List<CartItem> getItems() {
        return items;
    }

    public ShoppingCart setItems(List<CartItem> items) {
        this.items = items;
        return this;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public ShoppingCart setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public ShoppingCart setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }
}

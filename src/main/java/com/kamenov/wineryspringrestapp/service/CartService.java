package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.CartItem;
import com.kamenov.wineryspringrestapp.models.entity.ShoppingCart;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import jakarta.transaction.NotSupportedException;

import java.util.List;

public interface CartService {

    public ShoppingCart getActiveCartForUser(UserEntity user);

    public void addToCart (UserEntity user, Long wineId, int quantity) throws NotSupportedException;

    void removeFromCart(UserEntity user, Long cartItemId);

    ShoppingCart createNewCartForUser(UserEntity user);

    List<CartItem> getCartItems();


    List<CartItem> getCartItemsForUser(UserEntity user);

    CartItem findById(Long itemId);
}

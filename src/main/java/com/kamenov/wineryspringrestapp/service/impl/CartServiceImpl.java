package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.CartItem;
import com.kamenov.wineryspringrestapp.models.entity.ShoppingCart;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.repository.ShoppingCartRepository;
import com.kamenov.wineryspringrestapp.service.CartService;
import com.kamenov.wineryspringrestapp.service.WineService;
import jakarta.transaction.NotSupportedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CartServiceImpl implements CartService {
    private final ShoppingCartRepository shoppingCartRepository;

    private final WineService wineService;

    public CartServiceImpl(ShoppingCartRepository shoppingCartRepository, WineService wineService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.wineService = wineService;
    }

@Override
    public ShoppingCart getActiveCartForUser(UserEntity user) {
        return shoppingCartRepository.findByUserEntityAndCompletedFalse(user)
                .orElseGet(() -> createNewCartForUser(user));
    }
@Override
    public void addToCart(UserEntity user, Long wineId, int quantity) throws NotSupportedException {
        ShoppingCart cart = getActiveCartForUser(user);
        WineEntity wine = wineService.findWineById(wineId);
        CartItem item = new CartItem();
        item.setWine(wine);
        item.setQuantity(quantity);
        item.setCart(cart);
        cart.getItems().add(item);
        shoppingCartRepository.save(cart);
    }
@Override
    public void removeFromCart(UserEntity user, Long cartItemId) {
        ShoppingCart cart = getActiveCartForUser(user);
        cart.getItems().removeIf(item -> Objects.equals(item.getId(), cartItemId));
        shoppingCartRepository.save(cart);
    }
@Override
public ShoppingCart createNewCartForUser(UserEntity user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserEntity(user);
        return shoppingCartRepository.save(cart);
    }
}

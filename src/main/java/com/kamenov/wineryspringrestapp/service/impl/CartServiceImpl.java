package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.CartItem;
import com.kamenov.wineryspringrestapp.models.entity.ShoppingCart;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.repository.CartItemRepository;
import com.kamenov.wineryspringrestapp.repository.ShoppingCartRepository;
import com.kamenov.wineryspringrestapp.service.CartService;
import com.kamenov.wineryspringrestapp.service.WineService;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final WineService wineService;

    public CartServiceImpl(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository, WineService wineService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
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

        cartItemRepository.save(item);

        cart.getItems().add(item);
        shoppingCartRepository.save(cart);


    }

    @Transactional
    @Override
    public void removeFromCart(UserEntity user, Long cartItemId) {
        ShoppingCart cart = getActiveCartForUser(user);

        Optional<CartItem> itemToRemove = cart.getItems().stream()
                .filter(item -> Objects.equals(item.getId(), cartItemId))
                .findFirst();


        if (itemToRemove.isPresent()) {
            CartItem item = itemToRemove.get();

            cart.getItems().remove(item);

            cartItemRepository.deleteByCartAndItem(cart.getId(), item.getId());
            cartItemRepository.delete(item);
        }
        //cart.getItems().removeIf(item -> Objects.equals(item.getId(), cartItemId));
        shoppingCartRepository.save(cart);
//        if (removed){
//
//        }

    }

    @Override
    public ShoppingCart createNewCartForUser(UserEntity user) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserEntity(user);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }
    @Override
    public List<CartItem> getCartItemsForUser(UserEntity user) {
        ShoppingCart cart = getActiveCartForUser(user);
        return cartItemRepository.findByCart(cart);
    }


}

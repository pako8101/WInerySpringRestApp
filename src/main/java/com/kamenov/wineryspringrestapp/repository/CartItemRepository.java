package com.kamenov.wineryspringrestapp.repository;

import com.kamenov.wineryspringrestapp.models.entity.CartItem;
import com.kamenov.wineryspringrestapp.models.entity.ShoppingCart;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Modifying
    @Query("DELETE FROM CartItem sci WHERE sci.cart.id = :cartId AND sci.wine.id = :itemId")
    void deleteByCartAndItem(@Param("cartId") Long cartId, @Param("itemId") Long itemId);

    List<CartItem> findByCart(ShoppingCart cart);
}

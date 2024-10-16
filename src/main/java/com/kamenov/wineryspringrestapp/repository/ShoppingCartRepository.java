package com.kamenov.wineryspringrestapp.repository;

import com.kamenov.wineryspringrestapp.models.entity.ShoppingCart;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserEntityAndCompletedFalse(UserEntity user);


}

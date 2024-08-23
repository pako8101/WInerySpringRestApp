package com.kamenov.wineryspringrestapp.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class BoughtWineDto {

    @NotNull(message = "The price can not be zero")
    @Positive
    private BigDecimal price;
    @NotNull(message = "The name must be more then 3 letters")
    private String name;
    @NotNull(message = "The quantity must be positive")
    @Positive
    private int quantity;

    public BoughtWineDto() {
    }

    public @NotNull BigDecimal getPrice() {
        return price;
    }

    public BoughtWineDto setPrice(@NotNull BigDecimal price) {
        this.price = price;
        return this;
    }

    public @NotNull String getName() {
        return name;
    }

    public BoughtWineDto setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public int getQuantity() {
        return quantity;
    }

    public BoughtWineDto setQuantity(@NotNull int quantity) {
        this.quantity = quantity;
        return this;
    }
}

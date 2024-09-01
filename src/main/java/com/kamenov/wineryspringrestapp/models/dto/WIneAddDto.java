package com.kamenov.wineryspringrestapp.models.dto;

import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class WIneAddDto {
    private String name;

    private String description;

    private BigDecimal price;



    private Set<CategoryEnum> category;

    private BrandEntity brand;

    private int quantity;


    private int year;

    public WIneAddDto() {
    }

    public String getName() {
        return name;
    }

    public WIneAddDto setName(String name) {
        this.name = name;
        return this;
    }

    public Set<CategoryEnum> getCategory() {
        return category;
    }

    public WIneAddDto setCategory(Set<CategoryEnum> category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WIneAddDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public WIneAddDto setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }


    public BrandEntity getBrand() {
        return brand;
    }

    public WIneAddDto setBrand(BrandEntity brand) {
        this.brand = brand;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public WIneAddDto setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getYear() {
        return year;
    }

    public WIneAddDto setYear(int year) {
        this.year = year;
        return this;
    }
}

package com.kamenov.wineryspringrestapp.models.dto;

import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class WIneAddDto {


    private long id;
    @Size(min = 3,max = 100, message = "Article title must be between 3 and 100 characters")
    @NotNull
    private String name;
    @NotNull(message = "{add.article.content.message}")
    @Size(min = 10,message = "{add.article.content.message}")
    private String description;
    @NotNull(message = "You have to write price of wine!")
    @Positive(message = "The price should be positive!")
    private BigDecimal price;
@NotNull
    private String imageUrl;
@NotNull
    private Set<CategoryEnum> category;
@NotNull
    private BrandEntity brand;
    @NotNull(message = "You have to write quantity of wine!")
    @PositiveOrZero
    private int quantity;

@NotNull
@Positive
    private int year;

    public WIneAddDto() {
    }

    public long getId() {
        return id;
    }

    public WIneAddDto setId(long id) {
        this.id = id;
        return this;
    }

    public @NotNull String getImageUrl() {
        return imageUrl;
    }

    public WIneAddDto setImageUrl(@NotNull String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
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

    @Override
    public String toString() {
        return "WIneAddDto{" +
                "imageUrl='" + imageUrl + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", brand=" + brand +
                ", quantity=" + quantity +
                ", year=" + year +
                '}';
    }
}

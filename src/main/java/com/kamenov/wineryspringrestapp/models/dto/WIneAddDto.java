package com.kamenov.wineryspringrestapp.models.dto;

import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
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
    private List<CategoryEnum> category;
//@ManyToOne
//@JoinColumn(name = "brand_id")
//@NotNull(message = "Brand must not be null")
//    private BrandEntity brand;
    @NotNull(message = "You have to write quantity of wine!")
    @PositiveOrZero
    private Integer quantity;

@NotNull
@Positive(message = "Please input correct year")
    private Integer year;
    @NotNull(message = "You have to select a brand or create a new one")
    private Long brandId;

    // Fields for new brand creation (optional)
    private String newBrandName;
    private String newBrandDescription;
    public WIneAddDto() {
    }

    public @NotNull(message = "You have to write quantity of wine!") @PositiveOrZero Integer getQuantity() {
        return quantity;
    }

    public WIneAddDto setQuantity(@NotNull(message = "You have to write quantity of wine!") @PositiveOrZero Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public @NotNull @Positive(message = "Please input correct year") Integer getYear() {
        return year;
    }

    public WIneAddDto setYear(@NotNull @Positive(message = "Please input correct year") Integer year) {
        this.year = year;
        return this;
    }

    public Long getBrandId() {
        return brandId;
    }

    public WIneAddDto setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getNewBrandName() {
        return newBrandName;
    }

    public WIneAddDto setNewBrandName(String newBrandName) {
        this.newBrandName = newBrandName;
        return this;
    }

    public String getNewBrandDescription() {
        return newBrandDescription;
    }

    public WIneAddDto setNewBrandDescription(String newBrandDescription) {
        this.newBrandDescription = newBrandDescription;
        return this;
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

    public @NotNull List<CategoryEnum> getCategory() {
        return category;
    }

    public WIneAddDto setCategory(@NotNull List<CategoryEnum> category) {
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


//    public BrandEntity getBrand() {
//        return brand;
//    }
//
//    public WIneAddDto setBrand(BrandEntity brand) {
//        this.brand = brand;
//        return this;
//    }

//    public int getQuantity() {
//        return quantity;
//    }
//
//    public WIneAddDto setQuantity(int quantity) {
//        this.quantity = quantity;
//        return this;
//    }
//
//    public int getYear() {
//        return year;
//    }
//
//    public WIneAddDto setYear(int year) {
//        this.year = year;
//        return this;
//    }


}

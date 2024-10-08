package com.kamenov.wineryspringrestapp.models.service;

import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class WineServiceModel {
    private long id;

    private String name;

    private String description;

    private BigDecimal price;

    private String imageUrl;

    private List<CategoryEnum> category;

    private BrandEntity brand;

    private int quantity;



    private int year;

    public WineServiceModel() {
    }


    public long getId() {
        return id;
    }

    public WineServiceModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WineServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WineServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public WineServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public List<CategoryEnum> getCategory() {
        return category;
    }

    public WineServiceModel setCategory(List<CategoryEnum> category) {
        this.category = category;
        return this;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public WineServiceModel setBrand(BrandEntity brand) {
        this.brand = brand;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public WineServiceModel setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getYear() {
        return year;
    }

    public WineServiceModel setYear(int year) {
        this.year = year;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public WineServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public String toString() {
        return "WineServiceModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", category=" + category +
                ", brand=" + brand +
                ", quantity=" + quantity +
                ", year=" + year +
                '}';
    }
}

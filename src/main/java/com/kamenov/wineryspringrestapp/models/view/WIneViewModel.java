package com.kamenov.wineryspringrestapp.models.view;

import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import jakarta.validation.constraints.NotNull;

public class WIneViewModel {
    private  long id;

    private String name;
    private BrandEntity brand;
    private String imageUrl;
    private int quantity;
    private int price;

    private int year;
    private CategoryEnum category;
    private String description;

    public WIneViewModel() {
    }

    public int getPrice() {
        return price;
    }

    public WIneViewModel setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public WIneViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public long getId() {
        return id;
    }

    public WIneViewModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WIneViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public WIneViewModel setBrand(BrandEntity brand) {
        this.brand = brand;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public WIneViewModel setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getYear() {
        return year;
    }

    public WIneViewModel setYear(int year) {
        this.year = year;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public WIneViewModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WIneViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "WIneViewModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand=" + brand +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                ", year=" + year +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}

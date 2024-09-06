package com.kamenov.wineryspringrestapp.models.view;

import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;

public class WineDetailsViewModel {
    private  long id;

    private String name;
    private BrandEntity brand;
    private String imageUrl;
    private int quantity;
private int price;
    private int year;
    private CategoryEnum category;
    private String description;

    public WineDetailsViewModel() {
    }

    public int getPrice() {
        return price;
    }

    public WineDetailsViewModel setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public WineDetailsViewModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public long getId() {
        return id;
    }

    public WineDetailsViewModel setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public WineDetailsViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public WineDetailsViewModel setBrand(BrandEntity brand) {
        this.brand = brand;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public WineDetailsViewModel setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public int getYear() {
        return year;
    }

    public WineDetailsViewModel setYear(int year) {
        this.year = year;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public WineDetailsViewModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WineDetailsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "WineDetailsViewModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand=" + brand +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", year=" + year +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}

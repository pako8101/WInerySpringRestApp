package com.kamenov.wineryspringrestapp.models.entity;

import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "wines")
@Entity
public class WineEntity extends BaseEntity {

    private String name;
@Column(columnDefinition = "TEXT")
@NotEmpty
    private String description;
    @NotNull
    private BigDecimal price;
//    @Enumerated(EnumType.STRING)
//    private CategoryEnum category;

    @OneToMany
    private List<CategoryEntity> category;
    @NotNull
    @ManyToOne
    private BrandEntity brand;

    private int quantity;

    @NotNull
    private int year;

    public WineEntity() {

    }

    public int getQuantity() {
        return quantity;
    }

    public WineEntity setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getName() {
        return name;
    }

    public WineEntity setName(String name) {
        this.name = name;
        return this;
    }

    public @NotEmpty String getDescription() {
        return description;
    }

    public WineEntity setDescription(@NotEmpty String description) {
        this.description = description;
        return this;
    }

    public @NotNull BigDecimal getPrice() {
        return price;
    }

    public WineEntity setPrice(@NotNull BigDecimal price) {
        this.price = price;
        return this;
    }

    public List<CategoryEntity> getCategory() {
        return category;
    }

    public WineEntity setCategory(List<CategoryEntity> category) {
        this.category = category;
        return this;
    }

    public @NotNull BrandEntity getBrand() {
        return brand;
    }

    public WineEntity setBrand(@NotNull BrandEntity brand) {
        this.brand = brand;
        return this;
    }

    @NotNull
    public int getYear() {
        return year;
    }

    public WineEntity setYear(@NotNull int year) {
        this.year = year;
        return this;
    }

    @Override
    public String toString() {
        return "WineEntity{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", brand=" + brand +
                ", quantity=" + quantity +
                ", year=" + year +
                '}';
    }
}

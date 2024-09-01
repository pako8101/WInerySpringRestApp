package com.kamenov.wineryspringrestapp.models.entity;

import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true,nullable = false)
    private CategoryEnum name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private BrandEntity brand;

    public CategoryEntity() {
    }

    public CategoryEnum getName() {
        return name;
    }

    public CategoryEntity setName(CategoryEnum name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public CategoryEntity setBrand(BrandEntity brand) {
        this.brand = brand;
        return this;
    }
}

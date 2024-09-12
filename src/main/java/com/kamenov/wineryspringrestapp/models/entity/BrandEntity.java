package com.kamenov.wineryspringrestapp.models.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "brands")
@NamedEntityGraph(
        name = "brandWithCategories",
        attributeNodes = @NamedAttributeNode("categories")
)
public class BrandEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;
    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "brand"
    )

    private List<CategoryEntity> categories;


    public BrandEntity() {

    }

    public BrandEntity(String name, String description, List<CategoryEntity> categories) {
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public BrandEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public BrandEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public BrandEntity setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
        return this;
    }
    public void addCategory(CategoryEntity category) {
        category.setBrand(this);
        this.categories.add(category);
    }
}

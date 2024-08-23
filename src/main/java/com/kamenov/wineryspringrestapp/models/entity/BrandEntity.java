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

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "brand"
    )
    private List<CategoryEntity> categories;


    public BrandEntity() {

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
}

package com.kamenov.wineryspringrestapp.models.dto;

import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class BrandDto {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 1, message = "Name must not be empty")
   private String name;
    @NotNull
   private String description;
//    @OneToMany
//  private   List<CategoryEntity> categories =new ArrayList<>();


    public BrandDto() {
    }

    public BrandDto(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Long getId() {
        return id;
    }

    public BrandDto setId(Long id) {
        this.id = id;
        return this;
    }

    public @NotNull String getName() {
        return name;
    }

    public BrandDto setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public BrandDto setDescription(@NotNull String description) {
        this.description = description;
        return this;
    }

//    public List<CategoryEntity> getCategories() {
//        return categories;
//    }
//
//    public BrandDto setCategories(List<CategoryEntity> categories) {
//        this.categories = categories;
//        return this;
//    }
}

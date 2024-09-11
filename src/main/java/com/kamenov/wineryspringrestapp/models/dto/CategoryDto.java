package com.kamenov.wineryspringrestapp.models.dto;

import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryDto {
    private Long id;
    @NotNull
    @Size(min = 1, message = "Name must not be empty")
    private String name;

    @NotNull
    private String description;

    public CategoryDto() {
    }

    public Long getId() {
        return id;
    }

    public CategoryDto setId(Long id) {
        this.id = id;
        return this;
    }

    public @NotNull @Size(min = 1, message = "Name must not be empty") String getName() {
        return name;
    }

    public CategoryDto setName(@NotNull @Size(min = 1, message = "Name must not be empty") String name) {
        this.name = name;
        return this;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public CategoryDto setDescription(@NotNull String description) {
        this.description = description;
        return this;
    }
}

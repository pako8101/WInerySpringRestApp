package com.kamenov.wineryspringrestapp.models.dto;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BrandDto(
        @NotNull
        String name,
                       @OneToMany
                       List<CategoryDto> categories) {
}

package com.kamenov.wineryspringrestapp.models.dto;

import java.util.List;

public record BrandDto(String name, List<CategoryDto> categories) {
}

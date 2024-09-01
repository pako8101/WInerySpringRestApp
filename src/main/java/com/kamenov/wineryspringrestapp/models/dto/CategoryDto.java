package com.kamenov.wineryspringrestapp.models.dto;

import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;

public record CategoryDto(long id, CategoryEnum name) {
}

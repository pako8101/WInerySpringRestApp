package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryEntity findCategoryByName(CategoryEnum categoryEnum);

    void seedCategories();

    List<CategoryEntity> getAllCategories();

    CategoryEntity getCategoryById(Long id);
}

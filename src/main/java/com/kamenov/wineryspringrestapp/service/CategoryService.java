package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;

public interface CategoryService {
    CategoryEntity findCategoryByName(CategoryEnum categoryNameEnum);

    void seedCategories();
}

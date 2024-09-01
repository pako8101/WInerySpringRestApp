package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import com.kamenov.wineryspringrestapp.repository.CategoryRepository;
import com.kamenov.wineryspringrestapp.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity findCategoryByName(CategoryEnum categoryNameEnum) {
        return categoryRepository.findByName(categoryNameEnum)
                .orElse(null);
    }

    @Override
    public void seedCategories() {
        if (this.categoryRepository.count() == 0) {
            List<CategoryEntity> categories =
                    Arrays.stream(CategoryEnum.values())
              //      .map(CategoryEntity::new)
                .map(type -> new CategoryEntity())
                    .collect(Collectors.toList());
            this.categoryRepository.saveAllAndFlush(categories);
        }
    }
}

package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.CategoryNotFoundException;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.repository.CategoryRepository;
import com.kamenov.wineryspringrestapp.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
private final BrandRepository brandRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository, BrandRepository brandRepository) {
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public CategoryEntity findCategoryByName(CategoryEnum categoryEnum) {
        return categoryRepository.findByName(categoryEnum)
                .orElse(null);
    }
@Transactional
    @Override
    public void seedCategories() {
        if (this.categoryRepository.count() == 0) {
            List<CategoryEntity> categories =
                    Arrays.stream(CategoryEnum.values())
                            .map(type -> {
                                CategoryEntity categoryEntity = new CategoryEntity();
                                categoryEntity.setName(type); // Assign enum value to name
                                categoryEntity.setDescription("wine");

                                // Създай BrandEntity с валидни стойности за name и description
                                BrandEntity brand = new BrandEntity();
                                brand.setName("Default Brand");
                                brand.setDescription("Default Brand Description");
                                categoryEntity.setBrand(brand);

                                return categoryEntity;
                            })
                            .collect(Collectors.toList());
            this.categoryRepository.saveAllAndFlush(categories);

        }
//            List<CategoryEntity> categories =
//                    Arrays.stream(CategoryEnum.values())
//
//              //      .map(CategoryEntity::new)
//             //   .map(type -> new CategoryEntity())
//                            .map(type -> {
//                                CategoryEntity categoryEntity = new CategoryEntity();
//                                categoryEntity.setName(type); // Assign enum value to name
//                                categoryEntity.setDescription("wine");
//                                categoryEntity.setBrand(new BrandEntity()); // Set brand if needed
//                                return categoryEntity;
//                            })
//                    .collect(Collectors.toList());
//            this.categoryRepository.saveAllAndFlush(categories);
//        }
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }
    @Override
    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository.findById(id).
                orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

}

package com.kamenov.wineryspringrestapp.seeders;

import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import com.kamenov.wineryspringrestapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategorySeeder implements CommandLineRunner {

    private final CategoryService categoryService;
@Autowired
    public CategorySeeder(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
//        CategoryEntity category = new CategoryEntity();
//        category.setName(CategoryEnum.ROSE);
//        category.setDescription("wine white");
//
//        BrandEntity brand = new BrandEntity();
//        brand.setName("Test Brand");
//        brand.setDescription("Test Description");
//        category.setBrand(brand);
//
//        category.setBrand(brand); // example brand id
//
//        if (category.getName() == null) {
//            throw new IllegalArgumentException("Category name cannot be null");
//        }
      //  categoryService.seedCategories();
    }
}

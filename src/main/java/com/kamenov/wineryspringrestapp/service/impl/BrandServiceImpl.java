package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.BrandNotFoundException;
import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.dto.CategoryDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public List<BrandDto> getAllBrands() {
        return brandRepository.getAllBrands().stream()
                .map(brand -> new BrandDto(
                        brand.getName(),
                        brand.getDescription(),
                        brand.getCategories().stream()
                                .map(category -> new CategoryEntity())
                                .sorted(Comparator.comparing(CategoryEntity::getName))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator.comparing(BrandDto::getName))
                .collect(Collectors.toList());
    }
@Override
    public BrandEntity getBrandById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }
@Override
    public BrandEntity createBrand(BrandDto brandDTO) {
        BrandEntity brand = new BrandEntity();
    brand.setName(brandDTO.getName());
    for (CategoryEntity categoryEntity : brandDTO.getCategories()) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryEntity.getName());
        category.setDescription(categoryEntity.getDescription());
        brand.addCategory(category);
    }
//        brand.setName(brandDTO.getName());
//        brand.setDescription(brandDTO.getDescription());
//        brand.addCategory(brandDTO.getCategories().getFirst());
       // brand.setCategories(brandDTO.getCategories());
        return brandRepository.save(brand);
    }
}

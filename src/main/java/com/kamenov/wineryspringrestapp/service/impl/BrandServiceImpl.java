package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.dto.CategoryDto;
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
                        brand.getCategories().stream()
                                .map(category -> new CategoryDto(category.getId(), category.getName()))
                                .sorted(Comparator.comparing(CategoryDto::name))
                                .collect(Collectors.toList())
                ))
                .sorted(Comparator.comparing(BrandDto::name))
                .collect(Collectors.toList());
    }

}

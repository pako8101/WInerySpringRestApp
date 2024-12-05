package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<BrandEntity>getAllBrands();

    BrandEntity createBrand(BrandDto newBrandDTO);
    public BrandEntity getBrandById(Long id);

    void deleteBrand(Long id);

    BrandDto fetchBrand();

    boolean hasInitialized();

    void  updateBrand(BrandDto brandDTO);

    BrandEntity findByName(String name);

    BrandEntity save(BrandEntity brand);

    // Optional<String> finBrand(String brandName);

}

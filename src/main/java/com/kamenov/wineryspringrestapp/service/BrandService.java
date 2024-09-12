package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;

import java.util.List;

public interface BrandService {
    List<BrandEntity>getAllBrands();

    BrandEntity createBrand(BrandDto newBrandDTO);
    public BrandEntity getBrandById(Long id);

    void deleteBrand(Long id);

    BrandDto fetchBrand();

    boolean hasInitialized();

    void  updateBrand(BrandDto brandDTO);


}

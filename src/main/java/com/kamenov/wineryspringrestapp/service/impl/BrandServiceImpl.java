package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.BrandNotFoundException;
import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.service.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {
    private final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);
    private final BrandRepository brandRepository;
private final RestClient restClient;
    public BrandServiceImpl(BrandRepository brandRepository, RestClient restClient) {
        this.brandRepository = brandRepository;
        this.restClient = restClient;
    }


    @Override
    public List<BrandEntity> getAllBrands() {

        return brandRepository.findAll();
//        return brandRepository.getAllBrands().stream()
//                .map(brand -> new BrandEntity(
//                        brand.getName(),
//                        brand.getDescription(),
//                        brand.getCategories().stream()
//                                .map(category -> new CategoryEntity())
//                                .sorted(Comparator.comparing(CategoryEntity::getName))
//                                .collect(Collectors.toList())
//                ))
//                .sorted(Comparator.comparing(BrandEntity::getName))
//                .collect(Collectors.toList());
    }
@Override
    public BrandEntity getBrandById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundException("Brand not found"));
    }
@Override
    public BrandEntity createBrand(BrandDto brandDTO) {
    List<BrandEntity> existingBrands = brandRepository.findByName(brandDTO.getName());
    if (existingBrands.isEmpty()) {
        BrandEntity newBrand = new BrandEntity();
        newBrand.setName(brandDTO.getName());
        newBrand.setDescription(brandDTO.getDescription());
        newBrand.setCategories(brandDTO.getCategories());
        return brandRepository.save(newBrand);
    } else {
        if (!existingBrands.isEmpty()) {
            return existingBrands.get(0);
        } else {
            throw new BrandNotFoundException("Brand should exist but wasn't found");
        }
    }
//        BrandEntity brand = new BrandEntity();
//    brand.setName(brandDTO.getName());
//    for (CategoryEntity categoryEntity : brandDTO.getCategories()) {
//        CategoryEntity category = new CategoryEntity();
//        category.setName(categoryEntity.getName());
//        category.setDescription(categoryEntity.getDescription());
//        brand.addCategory(category);
//    }
//        brand.setName(brandDTO.getName());
//        brand.setDescription(brandDTO.getDescription());
//        brand.addCategory(brandDTO.getCategories().getFirst());
//        brand.setCategories(brandDTO.getCategories());
//        return brandRepository.save(brand);
    }
    @Override
    public void deleteBrand(Long id) {
        BrandEntity brand = getBrandById(id);
        brandRepository.delete(brand);
    }
    @Override
    public BrandDto fetchBrand() {

      return   restClient.
                get()
              .uri("/brands/all")
//                .uri("/brands/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(BrandDto.class);
    }
    @Override
    public boolean hasInitialized(){
        return brandRepository.count() > 0;
    }
    @Override
    public void  updateBrand(BrandDto brandDTO){

        LOGGER.info("Updating {} brand",brandDTO.getName());
        if (!brandRepository.existsById(brandDTO.getId())){
            throw new BrandNotFoundException("Brand that should be updated is not found on"
            + brandDTO.getName() + "but rather on " + brandDTO.getId());
        }

        brandDTO.getCategories()
                .forEach(categoryEntity -> {

                    CategoryEntity category = new CategoryEntity();
                                category.setName(categoryEntity.getName());
            category.setDescription(categoryEntity.getDescription());
            BrandEntity brand = getBrandById(brandDTO.getId());
            brand.addCategory(category);
                    brandRepository.save(brand);
                });


//        BrandEntity brand = getBrandById(brandDTO.getId());
//        brand.setName(brandDTO.getName());
//        for (CategoryEntity categoryEntity : brandDTO.getCategories()) {
//            CategoryEntity category = new CategoryEntity();
//            category.setName(categoryEntity.getName());
//            category.setDescription(categoryEntity.getDescription());
//            brand.addCategory(category);
//
//        }
//        brandRepository.save(brand);
    }



}

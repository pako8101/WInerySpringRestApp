package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.BrandNotFoundException;
import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.service.BrandService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Value("${brand-service.base-url}")
    private String brandServiceBaseUrl;

    private final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);
    private final BrandRepository brandRepository;
private final RestClient restClient;
    public BrandServiceImpl(BrandRepository brandRepository, RestClient restClient) {
        this.brandRepository = brandRepository;
        this.restClient = restClient;
    }


    @Override
    public List<BrandEntity> getAllBrands() {
List<BrandEntity> brands = brandRepository.findAll();
if (brands.isEmpty()) {

    WIneAddDto wineAddDto = new WIneAddDto();
    String newBrandDescription = wineAddDto.getNewBrandDescription();
    String newBrandName = wineAddDto.getNewBrandName();

    if (newBrandDescription == null) {
        newBrandDescription = "Bulgarian most popular winery";
    }
    if (newBrandName == null) {
        newBrandName = "Domayn Boyar";
    }
    BrandEntity brand = new BrandEntity();
    brand.setDescription(newBrandDescription);
    brand.setName(newBrandName);
    brandRepository.save(brand);


}
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
    @Transactional
@Override
    public BrandEntity createBrand(BrandDto brandDTO) {
    Optional<BrandEntity> existingBrands = brandRepository.findByName(brandDTO.getName());
    if (existingBrands.isEmpty()) {
        BrandEntity newBrand = new BrandEntity();
        newBrand.setName(brandDTO.getName());
        newBrand.setDescription(brandDTO.getDescription());
        //newBrand.setCategories(brandDTO.getCategories());
        return brandRepository.save(newBrand);
    } else {
        if (existingBrands.isPresent()) {
            return existingBrands.get();
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
              .uri(brandServiceBaseUrl +"/brands/all")
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
        if (brandDTO == null) {
            LOGGER.error("BrandDTO is null. Please check the input.");
            return;  // Излизаме от метода, ако brandDTO е null
        }
        LOGGER.info("Updating {} brand",brandDTO.getName());
        if (brandDTO.getName() != null) {
            String brandName = brandDTO.getName();
            if (!brandRepository.existsById(brandDTO.getId())){
                throw new BrandNotFoundException("Brand that should be updated is not found on"
                        + brandDTO.getName() + "but rather on " + brandDTO.getId());
            }

//            brandDTO.getCategories()
//                    .forEach(categoryEntity -> {
//
//                        CategoryEntity category = new CategoryEntity();
//                        category.setName(categoryEntity.getName());
//                        category.setDescription(categoryEntity.getDescription());
//
//                        brand.addCategory(category);
//
//                    });
            // Продължете с обработката
            BrandEntity brand = getBrandById(brandDTO.getId());
            brand.setName(brandName);
            brand.setDescription(brandDTO.getDescription());
            brandRepository.save(brand);
        } else {

            System.out.println("BrandDTO е null. Проверете REST заявката.");
        }



        BrandEntity brand = getBrandById(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brandRepository.save(brand);
//        for (CategoryEntity categoryEntity : brandDTO.getCategories()) {
//            CategoryEntity category = new CategoryEntity();
//            category.setName(categoryEntity.getName());
//            category.setDescription(categoryEntity.getDescription());
//            brand.addCategory(category);
//
//        }

    }

    @Override
    public BrandEntity findByName(String name) {
        return brandRepository.findByName(name).get();
    }


}

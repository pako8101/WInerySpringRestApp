package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.WineNotAuthorisedToEditException;
import com.kamenov.wineryspringrestapp.exceptions.WineNotFoundException;
import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;
import com.kamenov.wineryspringrestapp.models.user.UserSession;
import com.kamenov.wineryspringrestapp.models.view.WIneViewModel;
import com.kamenov.wineryspringrestapp.models.view.WineCategoryViewModel;
import com.kamenov.wineryspringrestapp.models.view.WineDetailsViewModel;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.repository.WineRepository;
import com.kamenov.wineryspringrestapp.service.BrandService;
import com.kamenov.wineryspringrestapp.service.CategoryService;
import com.kamenov.wineryspringrestapp.service.WineService;
import jakarta.transaction.NotSupportedException;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WineServiceImpl implements WineService {
    private final CategoryService categoryService;
    private final WineRepository wineRepository;
    private final ModelMapper modelMapper;
    private final UserSession userSession;
    private final BrandService brandService;
    private final BrandRepository brandRepository;
    private final Logger LOGGER =  LoggerFactory.getLogger(WineServiceImpl.class);

    public WineServiceImpl(CategoryService categoryService, WineRepository wineRepository, ModelMapper modelMapper, UserSession userSession, BrandService brandService, BrandRepository brandRepository) {
        this.categoryService = categoryService;
        this.wineRepository = wineRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
        this.brandService = brandService;
        this.brandRepository = brandRepository;
    }

    @Override
    public boolean buyWine(Long wineId, int quantity) {

        Optional<WineEntity> wineOpt = wineRepository.findById(wineId);
        if (wineOpt.isPresent()) {
            WineEntity wineToBuy = wineOpt.get();
            if (wineToBuy.getQuantity() >= quantity) {
                wineToBuy.setQuantity(wineToBuy.getQuantity() - quantity);
                wineRepository.save(wineToBuy);
                if (wineToBuy.getQuantity() <= 0) {
                    wineRepository.delete(wineToBuy);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<WineEntity> getWineById(Long wineId) {
        return wineRepository.findById(wineId);
    }

    @Override
    public void addWIne(WineServiceModel wineServiceModel, BrandEntity brand) {
        if (wineServiceModel == null || brand == null) {
            throw new IllegalArgumentException("WineServiceModel or BrandEntity cannot be null");
        }

        WineEntity wine = modelMapper.map(wineServiceModel, WineEntity.class);
        BrandDto brandDto = modelMapper.map(brand, BrandDto.class);
        BrandEntity newBrand = modelMapper.map(brandDto, BrandEntity.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (currentUsername == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        wine.setDescription(wineServiceModel.getDescription());
        wine.setName(wineServiceModel.getName());
        wine.setBrand(newBrand);
        if (wineServiceModel.getCategory() != null
                && !wineServiceModel.getCategory().isEmpty()) {
            wine.setCategory(wineServiceModel.getCategory().get(0));
        }else {
            throw new IllegalArgumentException("Category cannot be null");
        }




//                wineServiceModel.getCategory()
//                .stream()
//                .map(categoryService::findCategoryByName)
//                .collect(Collectors.toSet()));

        wineRepository.save(wine);

    }

    @Override
    public List<WineEntity> getAllWInes() {
        return wineRepository.findAll();
    }

    @Override
    @Transactional
    // @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        if (userSession.isAdmin()) {

        }
        wineRepository.deleteById(id);
    }

    @Override
    public WineDetailsViewModel getDetails(Long id) {
        WineEntity wine = wineRepository.findById(id)
                .orElseThrow(() ->
                        new WineNotFoundException("Article with id: " + id + " not found!"));
        WineDetailsViewModel dto = modelMapper.map(wine, WineDetailsViewModel.class);
        dto.setImageUrl("resources/static/images/wine6.png");
        return dto;
    }

    @Override
    public WineDetailsViewModel findWineBId(Long id) {
        return wineRepository.findById(id)
                .map(wine -> modelMapper.map(wine, WineDetailsViewModel.class))
                .orElseThrow(() ->
                        new WineNotFoundException("Not found wine with id: " + id));
    }

    @Override
    public WineEntity findWineById(Long id) {
        return wineRepository.findById(id)
                .map(wine -> modelMapper.map(wine, WineEntity.class))
                .orElseThrow(() -> new WineNotFoundException("Not found wine with id: " + id));
    }

    @Transactional
    @Override
    public WineEntity updateWine(Long id, WineEntity updatedWine) {
       // Optional<WineEntity> optionalWine = wineRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String currentUsername = authentication.getName();

//        if (optionalWine.isPresent()) {
//            WineEntity existingWine = optionalWine.get();

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                throw new WineNotAuthorisedToEditException("You are not authorized to edit this wine.");
            }
        WineEntity wine = wineRepository.findById(id)
                .orElseThrow(() -> new WineNotFoundException("Wine not found with id: " + id));

//
//        BrandEntity existingBrand = brandService.findByName(wine.getBrand().getName());
//            if (existingBrand != null) {
//                // Актуализирайте виното с този съществуващ бранд
//                wine.setBrand(existingBrand);
//            } else {
//                // Ако няма такъв бранд, създайте нов
//                BrandEntity newBrand = new BrandEntity();
//                newBrand.setName(wine.getBrand().getName());
//                newBrand.setDescription(wine.getBrand().getDescription());
//                brandRepository.save(newBrand);
//                wine.setBrand(newBrand);
//            }


        // Проверка дали брандът съществува
        BrandEntity brand = updatedWine.getBrand();
        if (brand != null && brand.getName() != null) {
            Optional<BrandEntity> existingBrand = brandRepository.findByName(brand.getName());
            if (existingBrand.isPresent()) {
                LOGGER.info("Brand '{}' already exists, using existing brand.", brand.getName());
                // Използвайте съществуващия бранд
                wine.setBrand(existingBrand.get());
            } else {
                LOGGER.info("Brand '{}' does not exist, creating new brand.", brand.getName());
                // Ако брандът не съществува, създайте нов
                BrandEntity newBrand = new BrandEntity();
                newBrand.setName(brand.getName());
                newBrand.setDescription(brand.getDescription());
                wine.setBrand(brandRepository.save(newBrand));
            }
        }


    wine.setImageUrl(updatedWine.getImageUrl());
    wine.setCategory(updatedWine.getCategory());

    wine.setPrice(updatedWine.getPrice());
    wine.setQuantity(updatedWine.getQuantity());
    wine.setName(updatedWine.getName());
    wine.setDescription(updatedWine.getDescription());
    wine.setCategory(updatedWine.getCategory());
    wine.setBrand(updatedWine.getBrand());  // Актуализиране на бранда

        return wineRepository.save(wine);


}
    @Override
    public List<WineCategoryViewModel> getAllByCategory(CategoryEnum categoryName) {
      List<WineEntity> wines = wineRepository.findAllByCategory(categoryName);
      List<WineCategoryViewModel> vieWInes = wines.stream()
              .map(wine -> modelMapper.map(wine, WineCategoryViewModel.class))
              .toList();

        return vieWInes;
    }

    @Override
    public WIneViewModel findById(Long id){
        return wineRepository.findWineById(id);
    }

    @Override
    public List<WIneViewModel> findAllWinesView() {
        return wineRepository
                .findAll()
                .stream()
                .map(wine -> {
                    WIneViewModel viewModel =
                            modelMapper.map(wine, WIneViewModel.class);

                    viewModel.
                            setImageUrl(wine.getImageUrl()
                                    .isEmpty() ?
                                    "resources/static/images/wine6.png" :
                                    wine.getImageUrl());

                    return viewModel;

                }).collect(Collectors.toList());
    }

}

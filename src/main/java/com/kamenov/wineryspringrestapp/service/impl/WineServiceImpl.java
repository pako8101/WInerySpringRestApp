package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;
import com.kamenov.wineryspringrestapp.repository.WineRepository;
import com.kamenov.wineryspringrestapp.service.CategoryService;
import com.kamenov.wineryspringrestapp.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WineServiceImpl implements WineService {
private final CategoryService categoryService;
    private final WineRepository wineRepository;
private final ModelMapper modelMapper;
    public WineServiceImpl(CategoryService categoryService, WineRepository wineRepository, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.wineRepository = wineRepository;
        this.modelMapper = modelMapper;
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
    public void  addWIne(WineServiceModel wineServiceModel) {
        WineEntity wine = modelMapper.map(wineServiceModel,WineEntity.class);

        //article.setAuthor(userService.findCurrentUserLoginEntity());
//        if (wine.g() == null) {
//            throw new IllegalArgumentException("User ID cannot be null");
//        }
        wine.setCategory(wineServiceModel.getCategory()
                .stream()
                .map(categoryService::findCategoryByName)
                .collect(Collectors.toSet()));

        wineRepository.save(wine);

    }
@Override
    public List<WineEntity> getAllWInes() {
        return wineRepository.findAll();
    }
}

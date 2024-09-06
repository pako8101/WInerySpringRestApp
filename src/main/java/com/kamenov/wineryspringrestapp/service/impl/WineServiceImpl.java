package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.WineNotAuthorisedToEditException;
import com.kamenov.wineryspringrestapp.exceptions.WineNotFoundException;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;
import com.kamenov.wineryspringrestapp.models.user.UserSession;
import com.kamenov.wineryspringrestapp.models.view.WIneViewModel;
import com.kamenov.wineryspringrestapp.models.view.WineDetailsViewModel;
import com.kamenov.wineryspringrestapp.repository.WineRepository;
import com.kamenov.wineryspringrestapp.service.CategoryService;
import com.kamenov.wineryspringrestapp.service.WineService;
import org.modelmapper.ModelMapper;
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

    public WineServiceImpl(CategoryService categoryService, WineRepository wineRepository, ModelMapper modelMapper, UserSession userSession) {
        this.categoryService = categoryService;
        this.wineRepository = wineRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
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
    public void addWIne(WineServiceModel wineServiceModel) {
        WineEntity wine = modelMapper.map(wineServiceModel, WineEntity.class);

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

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        if (userSession.isAdmin()) {
            wineRepository.deleteById(id);
        }
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

    @Override
    public WineEntity updateWine(Long id, WineEntity updatedWine) {
        Optional<WineEntity> optionalWine = wineRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       // String currentUsername = authentication.getName();

        if (optionalWine.isPresent()) {
            WineEntity existingWine = optionalWine.get();

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                throw new WineNotAuthorisedToEditException("You are not authorized to edit this wine.");
            }

            existingWine.setName(updatedWine.getName());
            existingWine.setDescription(updatedWine.getDescription());
            existingWine.setImageUrl(updatedWine.getImageUrl());
            existingWine.setCategory(updatedWine.getCategory());
            existingWine.setBrand(updatedWine.getBrand());
            existingWine.setPrice(updatedWine.getPrice());
            existingWine.setQuantity(updatedWine.getQuantity());

            return wineRepository.save(existingWine);
        } else {
            throw new WineNotFoundException("Wine not found with id: " + id);
        }
    }
}

package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.dto.BoughtWineDto;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.repository.WineRepository;
import com.kamenov.wineryspringrestapp.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class WineServiceImpl implements WineService {

    private final WineRepository wineRepository;
private final ModelMapper modelMapper;
    public WineServiceImpl(WineRepository wineRepository, ModelMapper modelMapper) {
        this.wineRepository = wineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean buyWine(Long wineId, int quantity, BoughtWineDto wineDto) {
        WineEntity wine = modelMapper.map(wineDto, WineEntity.class);
        Optional<WineEntity> wineOpt = wineRepository.findById(wine.getId());
        if (wineOpt.isPresent()) {
            WineEntity wineToBuy = wineOpt.get();
            if (wineToBuy.getQuantity() >= quantity) {
                wineToBuy.setQuantity(wineToBuy.getQuantity() - quantity);
                wineRepository.save(wineToBuy);
                return true;
            }
        }
        return false;
    }
    @Override
    public Optional<WineEntity> getWineById(Long wineId) {
        return wineRepository.findById(wineId);
    }
}

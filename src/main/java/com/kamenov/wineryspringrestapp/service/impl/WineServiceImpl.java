package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.repository.WineRepository;
import com.kamenov.wineryspringrestapp.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public WineEntity addWIne(WineEntity item) {
        return wineRepository.save(item);
    }
@Override
    public List<WineEntity> getAllWInes() {
        return wineRepository.findAll();
    }
}

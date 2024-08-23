package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.BoughtWineDto;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;

import java.util.Optional;

public interface WineService {

   // public boolean buyWine(Long wineId, int quantity);

    public boolean buyWine(Long wineId, int quantity, BoughtWineDto wineDto);

    Optional<WineEntity> getWineById(Long id);
}

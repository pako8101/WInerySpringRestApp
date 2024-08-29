package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.BoughtWineDto;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;

import java.util.List;
import java.util.Optional;

public interface WineService {

   // public boolean buyWine(Long wineId, int quantity);

    public boolean buyWine(Long wineId, int quantity);

    Optional<WineEntity> getWineById(Long id);

    WineEntity addWIne(WineEntity item);

    List<WineEntity> getAllWInes();
}

package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.BoughtWineDto;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;

import java.util.List;
import java.util.Optional;

public interface WineService {

   // public boolean buyWine(Long wineId, int quantity);

    public boolean buyWine(Long wineId, int quantity);

    Optional<WineEntity> getWineById(Long id);

    public void addWIne(WineServiceModel wineServiceModel);

    List<WineEntity> getAllWInes();
}

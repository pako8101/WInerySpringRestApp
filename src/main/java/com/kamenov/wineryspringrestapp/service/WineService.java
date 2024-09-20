package com.kamenov.wineryspringrestapp.service;

import com.kamenov.wineryspringrestapp.models.dto.BoughtWineDto;
import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;
import com.kamenov.wineryspringrestapp.models.view.WIneViewModel;
import com.kamenov.wineryspringrestapp.models.view.WineCategoryViewModel;
import com.kamenov.wineryspringrestapp.models.view.WineDetailsViewModel;
import jakarta.transaction.NotSupportedException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

public interface WineService {

   // public boolean buyWine(Long wineId, int quantity);

    public boolean buyWine(Long wineId, int quantity);

    Optional<WineEntity> getWineById(Long id);

    public void addWIne(WineServiceModel wineServiceModel, BrandEntity brand);

    List<WineEntity> getAllWInes();

    void delete(Long id);

    WineDetailsViewModel getDetails(Long id);

    WineDetailsViewModel findWineBId(Long id);

    WineEntity findWineById(Long id);

    public  WineEntity updateWine(Long id, WineEntity updatedWine);

    List<WineCategoryViewModel> getAllByCategory(CategoryEnum categoryName);

    WIneViewModel findById(Long id) throws NotSupportedException;

    List<WIneViewModel> findAllWinesView();
}

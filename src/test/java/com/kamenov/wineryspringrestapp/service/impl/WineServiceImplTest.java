package com.kamenov.wineryspringrestapp.service.impl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;
import com.kamenov.wineryspringrestapp.repository.WineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

public class WineServiceImplTest {

    @Mock
    private WineRepository wineRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private WineServiceImpl wineService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private WineServiceModel wineServiceModel;
    private BrandEntity brand;
    private WineEntity wineEntity;
    private BrandDto brandDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mocks
        wineServiceModel = new WineServiceModel();
        wineServiceModel.setName("Test Wine");
        wineServiceModel.setDescription("Test Description");
       wineServiceModel.setCategory(List.of(CategoryEnum.values()));

        brand = new BrandEntity();
        brand.setId(1L);

        wineEntity = new WineEntity();
        wineEntity.setName("Test Wine");

        brandDto = new BrandDto();
        brandDto.setId(1L);

        // Mock security context
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("testUser");
    }

    @Test
    public void testAddWineSuccess() {
        // Mock model mapping
        when(modelMapper.map(wineServiceModel, WineEntity.class)).thenReturn(wineEntity);
        when(modelMapper.map(brand, BrandDto.class)).thenReturn(brandDto);
        when(modelMapper.map(brandDto, BrandEntity.class)).thenReturn(brand);

        // Act
        wineService.addWIne(wineServiceModel, brand);

        // Verify that wine was saved
        verify(wineRepository, times(1)).save(wineEntity);
        assertEquals("Test Wine", wineEntity.getName());
        assertEquals(brand, wineEntity.getBrand());
    }

    @Test
    public void testAddWineWithNullWineServiceModelThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                wineService.addWIne(null, brand));

        assertEquals("WineServiceModel or BrandEntity cannot be null", exception.getMessage());
    }

    @Test
    public void testAddWineWithNullBrandThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                wineService.addWIne(wineServiceModel, null));

        assertEquals("WineServiceModel or BrandEntity cannot be null", exception.getMessage());
    }

    @Test
    public void testAddWineWithNullCategoryThrowsException() {
        // Arrange
        wineServiceModel.setCategory(null);

        when(modelMapper.map(wineServiceModel, WineEntity.class)).thenReturn(wineEntity);
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                wineService.addWIne(wineServiceModel, brand));

        assertEquals("Category cannot be null", exception.getMessage());
    }

    @Test
    public void testAddWineWithEmptyCategoryThrowsException() {
        // Arrange
        wineServiceModel.setCategory(Collections.emptyList());
when(modelMapper.map(wineServiceModel, WineEntity.class)).thenReturn(wineEntity);
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                wineService.addWIne(wineServiceModel, brand));

        assertEquals("Category cannot be null", exception.getMessage());
    }

    @Test
    public void testAddWineWithNullUserThrowsException() {
        // Mock no authenticated user
        when(authentication.getName()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                wineService.addWIne(wineServiceModel, brand));

        assertEquals("User ID cannot be null", exception.getMessage());
    }
}

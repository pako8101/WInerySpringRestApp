package com.kamenov.wineryspringrestapp.service.impl;

import com.kamenov.wineryspringrestapp.exceptions.BrandNotFoundException;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.web.BrandController;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.service.BrandService;
import com.kamenov.wineryspringrestapp.web.BrandController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BrandControllerTest {

    @Mock
    private BrandService brandService;
    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private BrandController brandController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllBrands() {
        // Arrange
        BrandEntity brand1 = new BrandEntity();
        brand1.setId(1L);
        brand1.setName("Brand 1");

        BrandEntity brand2 = new BrandEntity();
        brand2.setId(2L);
        brand2.setName("Brand 2");

        List<BrandEntity> brands = Arrays.asList(brand1, brand2);

        when(brandService.getAllBrands()).thenReturn(brands);

        // Act
        List<BrandEntity> result = brandController.getAllBrands();

        // Assert
        assertEquals(brands.size(), result.size());
        assertEquals(brands.get(0).getName(), result.get(0).getName());
        verify(brandService, times(1)).getAllBrands();
    }
    @Test
    public void brandNotFoundException() {
        when(brandRepository.findByName("boko")).thenReturn(Optional.empty());

        // Act & Assert: Expect BrandNotFoundException to be thrown
        Assertions.assertThrows(BrandNotFoundException.class, () -> brandService.findByName("boko"));

    }

    @Test
    public void testAddBrand_WithValidDto_ShouldRedirectToBrands() {
        // Arrange
        BrandDto brandDto = new BrandDto();
        brandDto.setName("New Brand");

        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        String result = brandController.addBrand(brandDto, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/brands", result);
        verify(brandService, times(1)).createBrand(brandDto);
        verifyNoInteractions(redirectAttributes);
    }

    @Test
    public void testAddBrand_WithInvalidDto_ShouldRedirectToAddPage() {
        // Arrange
        BrandDto brandDto = new BrandDto();
        brandDto.setName(""); // Simulate validation error

        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = brandController.addBrand(brandDto, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/brands/add", result);
        verify(redirectAttributes, times(1)).addFlashAttribute("brandDto", brandDto);
        verify(redirectAttributes, times(1))
                .addFlashAttribute("org.springframework.validation.BindingResult.brandDto", bindingResult);
        verify(brandService, never()).createBrand(any(BrandDto.class));
    }
}


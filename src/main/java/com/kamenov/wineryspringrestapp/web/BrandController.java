package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import com.kamenov.wineryspringrestapp.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    // Извличане на всички марки (brands) за попълване на селект опцията
    @GetMapping("/all")
    @ResponseBody
    public List<BrandEntity> getAllBrands() {
        return brandService.getAllBrands();
    }

    // Метод за добавяне на нова марка
    @PostMapping("/add")
    public String addBrand(@Valid @ModelAttribute("brandDto") BrandDto brandDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("brandDto", brandDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.brandDto", bindingResult);
            return "redirect:/brands/add";
        }

        brandService.createBrand(brandDto);
        return "redirect:/brands";
    }

}

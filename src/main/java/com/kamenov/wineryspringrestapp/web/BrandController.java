package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.repository.BrandRepository;
import org.springframework.stereotype.Controller;

@Controller
public class BrandController {

    private final BrandRepository brandRepository;

    public BrandController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }






}

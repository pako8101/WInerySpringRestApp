package com.kamenov.wineryspringrestapp.init;

import com.kamenov.wineryspringrestapp.service.BrandService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BrandInitializer implements CommandLineRunner {
    private final BrandService brandService;

    public BrandInitializer(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!brandService.hasInitialized()){

            brandService.updateBrand(brandService.fetchBrand());
        }
    }
}

package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.dto.BoughtWineDto;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.service.WineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.NoSuchElementException;

@Controller
public class WineController {
    @Autowired
    private final WineService wineService;
    private final ModelMapper modelMapper;

    public WineController(WineService wineService, ModelMapper modelMapper) {
        this.wineService = wineService;
        this.modelMapper = modelMapper;
    }
    @ModelAttribute()
    BoughtWineDto boughtWineDto (){
        return new BoughtWineDto();
    }

    @GetMapping("/wine/{id}")
    public String wine(@PathVariable Long id, Model model) {

        WineEntity wine = wineService.getWineById(id).orElseThrow(NoSuchElementException::new);

        model.addAttribute("wine", wine);
        return "wine";

    }
    @PostMapping("/item/buy/{id}")
    public String buyItem(@PathVariable Long id, Model model) {
        boolean success = wineService.buyWine(id, 1,boughtWineDto()); // Може да се добави поле за избор на количество
        if (success) {
            model.addAttribute("message", "Purchase successful!");
        } else {
            model.addAttribute("message", "Not enough items in stock.");
        }
        return "wines";
    }
}

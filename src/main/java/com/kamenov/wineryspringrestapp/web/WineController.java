package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.dto.BoughtWineDto;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;
import com.kamenov.wineryspringrestapp.service.WineService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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
    @ModelAttribute("boughtWineDto")
    BoughtWineDto boughtWineDto (){
        return new BoughtWineDto();
    }
    @ModelAttribute("wIneAddDto")
    WIneAddDto wIneAddDto (){
        return new WIneAddDto();
    }
    @GetMapping("/wines")
    public String viewWines(Model model) {
        List<WineEntity> items = wineService.getAllWInes();
        model.addAttribute("wines", items);
        return "wines";
    }
    @GetMapping("/wine/{id}")
    public String viewWine(@PathVariable Long id, Model model) {

        WineEntity wine = wineService.getWineById(id).orElseThrow(NoSuchElementException::new);

        model.addAttribute("wine", wine);
        return "wine";

    }
    @PostMapping("/wine/buy/{id}")
    public String buyItem(@PathVariable Long id, Model model) {
        boolean success = wineService.buyWine(id, 1); // Може да се добави поле за избор на количество
        if (success) {
            model.addAttribute("message", "Purchase successful!");
        } else {
            model.addAttribute("message", "Not enough items in stock.");
        }
        return "wines";
    }
    @GetMapping("/wine/add")
    public String addWIneForm(Model model) {
        model.addAttribute("item", new WineEntity());
        return "add-wine";
    }

    @PostMapping("/wine/add")
    public String addWine(@Valid WIneAddDto wIneAddDto,
                          Model model,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails principal) {
        if (principal.getUsername() == null) {
            throw  new UsernameNotFoundException("No user with that name subscribed");

        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wIneAddDto",wIneAddDto);
            redirectAttributes.addFlashAttribute("org.springframework" +
                    ".validation.BindingResult" +
                    ".wIneAddDto", bindingResult);
            return "redirect:add";
        }
WineServiceModel wineServiceModel = modelMapper.map(wIneAddDto, WineServiceModel.class);
        wineServiceModel.setCategory(wIneAddDto.getCategory());
        wineService.addWIne(wineServiceModel);
        model.addAttribute("message", "Wine added successfully!");
        return "wines";
    }
}

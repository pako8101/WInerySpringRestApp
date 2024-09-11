package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.exceptions.WineNotFoundException;
import com.kamenov.wineryspringrestapp.models.dto.BoughtWineDto;
import com.kamenov.wineryspringrestapp.models.dto.BrandDto;
import com.kamenov.wineryspringrestapp.models.dto.WIneAddDto;
import com.kamenov.wineryspringrestapp.models.entity.BrandEntity;
import com.kamenov.wineryspringrestapp.models.entity.CategoryEntity;
import com.kamenov.wineryspringrestapp.models.entity.WineEntity;
import com.kamenov.wineryspringrestapp.models.enums.CategoryEnum;
import com.kamenov.wineryspringrestapp.models.service.WineServiceModel;

import com.kamenov.wineryspringrestapp.models.view.WineCategoryViewModel;
import com.kamenov.wineryspringrestapp.models.view.WineDetailsViewModel;
import com.kamenov.wineryspringrestapp.service.BrandService;
import com.kamenov.wineryspringrestapp.service.CategoryService;
import com.kamenov.wineryspringrestapp.service.WineService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class WineController {
    @Autowired
    private final WineService wineService;
    private final ModelMapper modelMapper;
private final BrandService brandService;
private final CategoryService categoryService;
@Autowired
    public WineController(WineService wineService, ModelMapper modelMapper, BrandService brandService, CategoryService categoryService) {
        this.wineService = wineService;
        this.modelMapper = modelMapper;
        this.brandService = brandService;
    this.categoryService = categoryService;
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
        List<WineEntity> wines = wineService.getAllWInes();
        model.addAttribute("wines", wines);
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
        model.addAttribute("wine", new WIneAddDto());
        model.addAttribute("categories",categoryService.getAllCategories());
        model.addAttribute("brands",
                brandService.getAllBrands());

        return "add-wine";
    }

    @PostMapping("/wine/add")
    public String addWine(@Valid WIneAddDto wIneAddDto,
                          Model model,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails principal) {
        BrandEntity brand;
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

        if (wIneAddDto.getNewBrandName() != null && !wIneAddDto.getNewBrandName().isEmpty()) {
            // Create a new brand if provided
            BrandDto newBrandDTO = new BrandDto();
            newBrandDTO.setName(wIneAddDto.getNewBrandName());
            newBrandDTO.setDescription(wIneAddDto.getNewBrandDescription());
            brand = brandService.createBrand(newBrandDTO);

        } else if (wIneAddDto.getBrandId() != null) {
            // Избор на съществуващ бранд
            brand = brandService.getBrandById(wIneAddDto.getBrandId());
            if (brand == null) {
                bindingResult.rejectValue("brandId", "error.brandId", "Brand not found!");
                return "redirect:/wine/add";
            }
        } else {

            bindingResult.rejectValue("brandId", "error.brandId", "You must select an existing brand or create a new one.");
            return "redirect:/wine/add";

        }

        // Add the wine with the selected or created brand
//        wineService.addWine(wineDTO, brand);
        System.out.println("Brand ID: " + wIneAddDto.getBrandId());
        System.out.println("New Brand Name: " + wIneAddDto.getNewBrandName());


        WineServiceModel wineServiceModel = modelMapper.map(wIneAddDto, WineServiceModel.class);
        wineServiceModel.setCategory(wIneAddDto.getCategory());
        wineService.addWIne(wineServiceModel,brand);
        model.addAttribute("message", "Wine added successfully!");
        return "redirect:/wines";
    }
    @GetMapping("/{categoryName}")
    public ModelAndView getByCategory(@PathVariable("categoryName") CategoryEnum categoryName) {
        List<WineCategoryViewModel> wines = wineService.getAllByCategory(categoryName);

        String view =
                switch (categoryName) {
                    case WHITE -> "white";
                    case RED -> "red";
                    case ROSE -> "rose";

                };

        ModelAndView modelAndView = new ModelAndView(view);

        modelAndView.addObject("wines", wines);

        return modelAndView;
    }
        @DeleteMapping("/{id}")
    public ResponseEntity<WIneAddDto> deleteById(@PathVariable("id") Long id,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        wineService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        WineDetailsViewModel wine =
                wineService.getDetails(id);

        if (wine == null) throw  new WineNotFoundException();

        model.addAttribute("wine",
                wineService.findWineBId(id));

        return "wine-details";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        WineEntity wine = wineService.findWineById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

//        if (!wine.getUser().getUsername().equals(currentUsername)) {
//            throw new WineNotAuthorisedToEditException(
//                    "You are not authorized to edit this article.");
//        }

        model.addAttribute("wine", wine);
        return "edit-wine";
    }

    @PatchMapping("/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute WineEntity wine) {
        wineService.updateWine(id, wine);
        return "redirect:/wines";
    }
}

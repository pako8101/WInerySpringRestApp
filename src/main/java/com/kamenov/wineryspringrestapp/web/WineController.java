package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.exceptions.WineNotAuthorisedToEditException;
import com.kamenov.wineryspringrestapp.exceptions.WineNotFoundException;
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

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

import static com.nimbusds.oauth2.sdk.util.StringUtils.isNumeric;

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
    BoughtWineDto boughtWineDto() {
        return new BoughtWineDto();
    }

    @ModelAttribute("wIneAddDto")
    WIneAddDto wIneAddDto() {
        return new WIneAddDto();
    }

    @GetMapping("/wines/all")
    public String viewWines(Model model) {
        List<WIneViewModel> wines = wineService.findAllWinesView();
        model.addAttribute("wines", wines);
        return "wines";
    }

    //    @GetMapping("/wines/{id}")
//    public String viewWine(@PathVariable Long id, Model model) {
//
//        WineEntity wine = wineService.getWineById(id).orElseThrow(WineNotFoundException::new);
//
//        model.addAttribute("wine", wine);
//        return "wines";
//
//    }
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
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brands",
                brandService.getAllBrands());

        return "add-wine";
    }

    @PostMapping("/wine/add")
    public String addWine(@Valid WIneAddDto wIneAddDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails principal,
                          @RequestParam Long brandId) {

        if (principal.getUsername() == null) {
            throw new UsernameNotFoundException("No user with that name subscribed");

        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("wIneAddDto", wIneAddDto);
            redirectAttributes.addFlashAttribute("org.springframework" +
                    ".validation.BindingResult" +
                    ".wIneAddDto", bindingResult);
            return "redirect:add";
        }
        BrandEntity brand = null;

        // Handle existing brand selection
        if (brandId != null) {
            brand = brandService.getBrandById(brandId);
            if (brand == null) {
                bindingResult.rejectValue("brandId", "error.brandId", "Selected brand does not exist!");
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wineAddDto", bindingResult);
                return "redirect:/wine/add";
            }
        } else if (wIneAddDto.getNewBrandName() != null && !wIneAddDto.getNewBrandName().isEmpty()) {
            // Handle new brand creation
            brand = brandService.findByName(wIneAddDto.getNewBrandName());
            if (brand == null) {
                BrandDto newBrandDto = new BrandDto();
                newBrandDto.setName(wIneAddDto.getNewBrandName());
                newBrandDto.setDescription(wIneAddDto.getNewBrandDescription());
                brand = brandService.createBrand(newBrandDto);
            }
        } else {
            bindingResult.rejectValue("brandId", "error.brandId", "You must select an existing brand or create a new one.");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wineAddDto", bindingResult);
            return "redirect:/wine/add";
        }

        // Map DTO to service model and save the wine
        WineServiceModel wineServiceModel = modelMapper.map(wIneAddDto, WineServiceModel.class);
        wineServiceModel.setBrand(brand);

        wineService.addWIne(wineServiceModel,brand);

        redirectAttributes.addFlashAttribute("message", "Wine added successfully!");
        return "redirect:/wines/all";
//        if (brandId == null && wIneAddDto.getNewBrandName() != null
//                && !wIneAddDto.getNewBrandName().isEmpty()) {
//
//            BrandEntity existingBrand = brandService.findByName(wIneAddDto.getNewBrandName());
//
//            if (existingBrand != null) {
//                brand = existingBrand;
//            } else {
//                // Create a new brand if provided
//                BrandDto newBrandDTO = new BrandDto();
//                brandDto.setName(wIneAddDto.getNewBrandName());
//                brandDto.setDescription(wIneAddDto.getNewBrandDescription());
//                brand = brandService.createBrand(brandDto);
//            }
//
//
//        } else if (brandId != null) {
//            // Избор на съществуващ бранд
//            brand = brandService.getBrandById(brandId);
//            return "redirect:/wine/add";
//        }
//
//        if (brand == null) {
//            brandService.createBrand(brandDto);
//            bindingResult.rejectValue("brandId", "error.brandId", "Brand not found!");
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wIneAddDto", bindingResult);
//            return "redirect:/wine/add";
//
//
//        } else {
//
//            bindingResult.rejectValue("brandId", "error.brandId", "You must select an existing brand or create a new one.");
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wIneAddDto", bindingResult);
//            return "redirect:/wine/add";
//
//
//        }
////        if (wIneAddDto.getBrandId() == null || !isNumeric(wIneAddDto.getBrandId().toString())) {
////            throw new IllegalArgumentException("Invalid brandId: must be a number");
////
////        }
////
////            // Друг код за обработка на виното
//
//
//            WineServiceModel wineServiceModel = modelMapper.map(wIneAddDto, WineServiceModel.class);
//            wineServiceModel.setBrand(brand);
//            wineService.addWIne(wineServiceModel, brand);
//
//            model.addAttribute("message", "Wine added successfully!");
//            return "redirect:/wines/all";


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

    @DeleteMapping("wine/{id}")
    public String deleteById(@PathVariable("id") Long id,
                             @AuthenticationPrincipal UserDetails userDetails) {
        wineService.delete(id);

        return "redirect:/wines/all";
//        return ResponseEntity
//                .noContent()
//                .build();
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        WineDetailsViewModel wine =
                wineService.getDetails(id);

        if (wine == null) throw new WineNotFoundException();

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
        if (wine == null) {
            throw new WineNotAuthorisedToEditException("Wine not found with id: " + id);
        }

        List<CategoryEntity> categories = categoryService.findAll();

        model.addAttribute("wine", wine);
        model.addAttribute("categories", categories);
        return "edit-wine";
    }

    @PatchMapping("wines/{id}")
    public String updateWine(@PathVariable Long id,
                             @ModelAttribute WineEntity wine,
                             @ModelAttribute WIneAddDto wineUpdateDto,
                             @AuthenticationPrincipal UserDetails principal
            , BindingResult result) {
        if (result.hasErrors()) {
            return "edit-wine";
        }
        if (principal == null || !principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new WineNotAuthorisedToEditException("Only admins can edit wine.");
        }
        if (wineUpdateDto.getNewBrandName() != null && !wineUpdateDto.getNewBrandName().isEmpty()) {
            // Създаване на нов бранд, ако е въведен нов
            BrandDto newBrandDto = new BrandDto();
            newBrandDto.setName(wineUpdateDto.getNewBrandName());
            newBrandDto.setDescription(wineUpdateDto.getNewBrandDescription());
            BrandEntity newBrand = brandService.createBrand(newBrandDto);
            wine.setBrand(newBrand);
        } else if (wineUpdateDto.getBrandId() != null) {
            // Актуализиране на бранда със съществуващ
            BrandEntity existingBrand = brandService.getBrandById(wineUpdateDto.getBrandId());
            if (existingBrand == null) {
                result.rejectValue("brandId", "error.brandId", "Brand not found.");
                return "redirect:/wines/edit/" + id;
            }
//        if (wineService.existsByName(wine.getName()) && !wine.getId().equals(id)) {
//            result.rejectValue("name", "error.wine", "Wine with this name already exists.");
//            return "edit-wine";
       }
            wineService.updateWine(id, wine);
            return "redirect:/wines/all";
        }
    }


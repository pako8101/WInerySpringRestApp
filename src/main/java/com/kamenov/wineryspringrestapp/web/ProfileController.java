package com.kamenov.wineryspringrestapp.web;


import com.kamenov.wineryspringrestapp.models.view.UserViewModel;
import com.kamenov.wineryspringrestapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class ProfileController {
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;

    }
    @GetMapping("/profile")
    public ModelAndView profile() {
        UserViewModel userProfileViewModel = userService. getUserProfile();

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("userProfileViewModel", userProfileViewModel);

        return modelAndView;
    }

//    @GetMapping("/profile/{id}")
//    public ModelAndView profile(@PathVariable Long id) {
//        UserViewModel userViewModel = userService.findBId(id);
//
//        ModelAndView modelAndView = new ModelAndView("profile");
//        modelAndView.addObject("userProfileViewModel", userViewModel);
//
//        return modelAndView;
//    }



}

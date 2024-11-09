package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.user.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal AppUserDetails appUserDetails){

        if (appUserDetails != null) {
            model.addAttribute("fullName", appUserDetails.getFullName());
            model.addAttribute("age", appUserDetails.getAge());
        }

        return "index";
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    @GetMapping("/history")
    public String history() {
        return "history";
    }
//    @GetMapping("/wines")
//    public String wines() {
//        return "wines";
//    }
    @GetMapping("/wine")
    public String wine() {
        return "wine";
    }
    @GetMapping("/payment")
    public String pay() {
        return "payment-page";
    }

}

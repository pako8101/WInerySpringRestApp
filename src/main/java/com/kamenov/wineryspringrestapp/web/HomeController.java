package com.kamenov.wineryspringrestapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
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
    @GetMapping("/wines")
    public String wines() {
        return "wines";
    } @GetMapping("/wine")
    public String wine() {
        return "wine";
    }

}

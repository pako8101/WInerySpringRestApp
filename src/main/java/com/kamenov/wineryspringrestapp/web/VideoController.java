package com.kamenov.wineryspringrestapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VideoController {

    @GetMapping("/wine-video")
    public String wineVideo(@RequestParam(name = "id",
    required = false,defaultValue = "lRxOrS8b8BQ")String videoId, Model model) {
       model.addAttribute("videoId", videoId);
        return "wine-video";
    }

}

package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.dto.UserLoginDto;
import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import com.kamenov.wineryspringrestapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class RegisterController {
    private final UserService userService;
    private final SecurityContextRepository securityContextRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public RegisterController(UserService userService, SecurityContextRepository securityContextRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("userRegisterDto")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @ModelAttribute("userLoginDto")
    public UserLoginDto userLoginDto() {
        return new UserLoginDto();
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        if (!model.containsAttribute("userRegisterDto")) {
            model.addAttribute("userRegisterDto", new UserRegisterDto());
        }
        //  model.addAttribute("recaptchaSiteKey", recaptchaSiteKey);
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid UserRegisterDto userRegisterDto,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               Model model,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterDto.getPassword()
                .equals(userRegisterDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userRegisterDto", bindingResult);

            return "redirect:/users/register";
        }


        userService.registerUser(userRegisterDto, successfulAuth -> {
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);

            strategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
        });

        model.addAttribute("message", "Registration successful");
        return "redirect:/";
    }


}

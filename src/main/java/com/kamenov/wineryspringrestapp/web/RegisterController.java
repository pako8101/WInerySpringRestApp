package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.dto.UserLoginDto;
import com.kamenov.wineryspringrestapp.models.dto.UserRegisterDto;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.service.JwtService;
import com.kamenov.wineryspringrestapp.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private final ModelMapper modelMapper;
private final JwtService jwtService;
    @Autowired
    public RegisterController(UserService userService, SecurityContextRepository securityContextRepository, ModelMapper modelMapper, JwtService jwtService) {
        this.userService = userService;
        this.securityContextRepository = securityContextRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @ModelAttribute("userRegisterDto")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
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
        logger.info("Registering user: {}", userRegisterDto.getUsername());

        if (bindingResult.hasErrors() || !userRegisterDto.getPassword()
                .equals(userRegisterDto.getConfirmPassword())) {
            logger.warn("Registration failed for user: {}", userRegisterDto.getUsername());
            redirectAttributes.addFlashAttribute("registerDto",
                    userRegisterDto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult." +
                            "registerDto", bindingResult);

            return "redirect:/users/register";
        }

        UserEntity user =
        userService.registerUser(userRegisterDto, successfulAuth -> {
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();

            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(successfulAuth);

            strategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);

        });
        Cookie cookie = new Cookie("jwt", jwtService.generateToken(user));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        logger.debug("Username: " + userRegisterDto.getUsername());
        logger.debug("Password: " + userRegisterDto.getPassword());
        logger.debug("Login Error: " + bindingResult);
        model.addAttribute("message", "Registration successful");

        return "redirect:/";
    }


}

package com.kamenov.wineryspringrestapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(WineNotAuthorisedToEditException.class)
    public ModelAndView handleUnauthorizedException(WineNotAuthorisedToEditException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("unauthorized");
        return modelAndView ;  // това може да бъде страница с грешка, която показва съобщение
    }
    @ExceptionHandler(EmptyCartException.class)
    public String handleEmptyCartException(EmptyCartException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "/cart-view"; // thymeleaf страница за празната количка
    }
}


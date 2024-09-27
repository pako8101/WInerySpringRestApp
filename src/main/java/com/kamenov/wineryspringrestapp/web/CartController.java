package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.entity.ShoppingCart;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.service.CartService;
import jakarta.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
@Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @ModelAttribute("cart")
    public ShoppingCart getCart() {
    return new ShoppingCart();
    }

    @GetMapping
    public String showCart(Model model, @AuthenticationPrincipal UserEntity user) {
        ShoppingCart cart = cartService.getActiveCartForUser(user);
        model.addAttribute("cart", cart);
        return "/cart-view"; // thymeleaf страница за кошницата
    }

    @PostMapping("/add/{id}")
    public String addToCart(@AuthenticationPrincipal UserEntity user,
                            @PathVariable("id") Long wineId,
                            @RequestParam(defaultValue = "1") int quantity) throws NotSupportedException {
        cartService.addToCart(user, wineId, quantity);
        return "redirect:/wines/all";
    }

    @PostMapping("/remove")
    public String removeFromCart(@AuthenticationPrincipal UserEntity user, @RequestParam Long cartItemId) {
        cartService.removeFromCart(user, cartItemId);
        return "redirect:/wines/all";
    }
    @ModelAttribute("cart")
    public ShoppingCart populateCart(@AuthenticationPrincipal UserEntity user) {
        return cartService.getActiveCartForUser(user);
    }
    @GetMapping("/date")
    public String getDateAfterTenDays(Model model) {
        // Вземи текущата дата
        LocalDate currentDate = LocalDate.now();

        // Добави 10 дни
        LocalDate futureDate = currentDate.plusDays(10);

        // Форматиране на датата по желание (не е задължително)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = futureDate.format(formatter);

        // Подаване на датата към Thymeleaf
        model.addAttribute("futureDate", formattedDate);

        return "cart-view";
    }
}

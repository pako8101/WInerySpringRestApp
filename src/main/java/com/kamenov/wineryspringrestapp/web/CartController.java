package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.exceptions.EmptyCartException;
import com.kamenov.wineryspringrestapp.models.entity.CartItem;
import com.kamenov.wineryspringrestapp.models.entity.ShoppingCart;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.service.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
   ;
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
        List<CartItem> cartItems = cartService.getCartItemsForUser(user);
        if (cartItems.isEmpty()) {
            throw new EmptyCartException("Your cart is empty.");

        }
        //    List<CartItem> cartItems = cartService.getCartItems();
        double total = cartItems.stream()
                        .mapToDouble(CartItem::getSubtotal).sum();

//       add date -----
        LocalDate currentDate = LocalDate.now();

        // Добави 10 дни
        LocalDate futureDate = currentDate.plusDays(10);

        // Форматиране на датата по желание (не е задължително)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = futureDate.format(formatter);

        // Подаване на датата към Thymeleaf
        model.addAttribute("futureDate", formattedDate);


       // ------
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
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
    public String removeFromCart(@AuthenticationPrincipal UserEntity user,
                                 @RequestParam("cartItemId") Long cartItemId,
                                 Model model, HttpSession session) {
        cartService.removeFromCart(user, cartItemId);

     //   cartItems.removeIf(item -> item.getWine().getId().equals(cartItemId));
        ShoppingCart cart = cartService.getActiveCartForUser(user);
        List<CartItem> cartItems = cart.getItems();

        session.setAttribute("cart", cart);

        double totalSum = cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        System.out.println("total sum: " + totalSum);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", totalSum);

        return "redirect:/cart";


    }
    @ModelAttribute("cart")
    public ShoppingCart populateCart(@AuthenticationPrincipal UserEntity user) {
        return cartService.getActiveCartForUser(user);
    }
//    @GetMapping("/date")
//    public String getDateAfterTenDays(Model model) {
//        // Вземи текущата дата
//        LocalDate currentDate = LocalDate.now();
//
//        // Добави 10 дни
//        LocalDate futureDate = currentDate.plusDays(10);
//
//        // Форматиране на датата по желание (не е задължително)
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String formattedDate = futureDate.format(formatter);
//
//        // Подаване на датата към Thymeleaf
//        model.addAttribute("futureDate", formattedDate);
//
//        return "/cart-view";
//    }

    @ExceptionHandler(EmptyCartException.class)
    public String handleEmptyCartException(EmptyCartException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "/cart-view"; // thymeleaf страница за празната количка
    }

}

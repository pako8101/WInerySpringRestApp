package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.exceptions.EmptyCartException;
import com.kamenov.wineryspringrestapp.models.entity.CartItem;
import com.kamenov.wineryspringrestapp.models.entity.Order;
import com.kamenov.wineryspringrestapp.models.entity.ShoppingCart;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.repository.ShoppingCartRepository;
import com.kamenov.wineryspringrestapp.service.CartService;
import com.kamenov.wineryspringrestapp.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.Transactional;
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
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
private final ShoppingCartRepository shoppingCartRepository;
    private final CartService cartService;
private final PaymentService paymentService;
@Autowired
    public CartController(ShoppingCartRepository shoppingCartRepository, CartService cartService, PaymentService paymentService) {
    this.shoppingCartRepository = shoppingCartRepository;
    this.cartService = cartService;
    this.paymentService = paymentService;
}
    @ModelAttribute("cart")
    public ShoppingCart getCart() {
    return new ShoppingCart();
    }

    @GetMapping
    public String showCart(Model model, @AuthenticationPrincipal UserEntity user) {
        ShoppingCart cart = cartService.getActiveCartForUser(user);
        List<CartItem> cartItems = cartService.getCartItemsForUser(user);
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
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
@Transactional
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
    @GetMapping("/payment/{itemId}")
    public String showPaymentPage(@PathVariable Long itemId, Model model,HttpSession session) {
        ShoppingCart cart = shoppingCartRepository.getReferenceById(itemId);
        List<CartItem> cartItems = cart.getItems();
        session.setAttribute("cart", cart);

        double totalSum = cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        System.out.println("total sum: " + totalSum);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", totalSum);
        return "payment-page";  // Промени на името на страницата за плащане
    }

    // Стъпка 3: Обработи плащането и завърши поръчката
    @PostMapping("/pay/{itemId}")
    public String processPayment(@PathVariable Long itemId, Model model,HttpSession session) {
    //date
        LocalDate currentDate = LocalDate.now();

        // Добави 10 дни
        LocalDate futureDate = currentDate.plusDays(10);

        // Форматиране на датата по желание (не е задължително)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = futureDate.format(formatter);

        // Подаване на датата към Thymeleaf
        model.addAttribute("futureDate", formattedDate);
        //------------------------------
        ShoppingCart cart = shoppingCartRepository.getReferenceById(itemId);
        List<CartItem> cartItems = cart.getItems();
        session.setAttribute("cart", cart);

        double totalSum = cartItems.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        System.out.println("total sum: " + totalSum);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", totalSum);
        boolean isPaymentSuccessful = paymentService.processPayment(itemId);
        if (isPaymentSuccessful) {
            return "redirect:pay/" + itemId;
        } else {
            model.addAttribute("error", "Плащането не беше успешно. Опитайте отново.");
            return "payment-page";
        }
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

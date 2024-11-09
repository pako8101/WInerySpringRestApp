package com.kamenov.wineryspringrestapp.web;

import com.kamenov.wineryspringrestapp.models.entity.Order;
import com.kamenov.wineryspringrestapp.models.entity.UserEntity;
import com.kamenov.wineryspringrestapp.service.OrderService;
import com.kamenov.wineryspringrestapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private final OrderService orderService;
@Autowired
    private final PaymentService paymentService;
    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal UserEntity user) {
        Order order = orderService.checkOut(user);
        return "redirect:/order/confirmation/" + order.getId();
    }
//    @GetMapping("/payment/{orderId}")
//    public String showPaymentPage(@PathVariable Long orderId, Model model) {
//        Order order = orderService.findById(orderId);
//        model.addAttribute("order", order);
//        return "payment-page";  // Промени на името на страницата за плащане
//    }
//
//    // Стъпка 3: Обработи плащането и завърши поръчката
//    @PostMapping("/pay/{orderId}")
//    public String processPayment(@PathVariable Long orderId, Model model) {
//        boolean isPaymentSuccessful = paymentService.processPayment(orderId);
//        if (isPaymentSuccessful) {
//            return "redirect:/order/confirmation/" + orderId;
//        } else {
//            model.addAttribute("error", "Плащането не беше успешно. Опитайте отново.");
//            return "payment-page";
//        }
//    }


    @GetMapping("/confirmation/{orderId}")
    public String orderConfirmation(@PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "redirect:/order/confirmation";
    }



}

package com.example.toktoralieva_orozbekova_duishenaliev.pizza.controller;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/history")
    public String getOrdersByUser(Model model, Principal principal){
        model.addAttribute("orders", orderService.getOrderByUser(principal.getName()));
        return "orders_history";
    }
//
//    @GetMapping("/history")
//    public String viewOrderHistory(Model model, Principal principal) {
//        List<Order> orders = orderService.getOrdersByUser(principal.getName());
//        model.addAttribute("orders", orders);
//        return "orders_history";
//    }
}

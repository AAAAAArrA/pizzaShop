package com.example.toktoralieva_orozbekova_duishenaliev.pizza.controller;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.CartDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CartService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.PizzaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final PizzaService pizzaService;

    public CartController(CartService cartService, PizzaService pizzaService) {
        this.cartService = cartService;
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public String getCart(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("cart", new CartDTO());
        }else {
            CartDTO cartDTO = cartService.getBucketByUser(principal.getName());
            model.addAttribute("cart", cartDTO);
        }
        return "cart";
    }

    @PostMapping
    public String createOrder(Principal principal){
        System.out.println("test");
        if(principal != null){
            cartService.commitBucketToOrder(principal.getName());

        }
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String deletePizzaFromCart(@PathVariable Long id, Principal principal){
        cartService.deletePizzaFromCart(id, principal.getName());
        return "redirect:/cart";
    }


}

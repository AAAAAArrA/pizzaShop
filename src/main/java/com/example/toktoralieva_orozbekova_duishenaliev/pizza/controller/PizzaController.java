package com.example.toktoralieva_orozbekova_duishenaliev.pizza.controller;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PizzaDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Pizza;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CartService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/menu")
public class PizzaController {

    private final PizzaService pizzaService;
    private final CartService cartService;


    @Autowired
    public PizzaController(PizzaService pizzaService, CartService cartService) {
        this.pizzaService = pizzaService;
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public String getInfoAboutOnePizza(@PathVariable Long id, Model model) {
        model.addAttribute("pizza", pizzaService.getPizzaById(id));
        return "pizza_detail";
    }

    @GetMapping("/bucket/{id}")
    public String addToCart(@ModelAttribute PizzaDTO pizza, @RequestParam Integer amount, @RequestParam String size, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        pizzaService.addToUserCart(pizza, principal.getName());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return "redirect:/";
    }

    @GetMapping("/add-pizza")
    public String createPizza(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizza_form";
    }

    @PostMapping("/save")
    public String savePizza(@RequestParam("file") MultipartFile file,
                            Pizza pizza) throws IOException {
        pizzaService.savePizzaToDB(pizza, file);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editPizza(@PathVariable Long id, Model model) {
        model.addAttribute("pizza", pizzaService.getPizzaById(id));
        return "pizza_form";
    }
}
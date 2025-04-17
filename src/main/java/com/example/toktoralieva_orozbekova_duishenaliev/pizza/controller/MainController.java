package com.example.toktoralieva_orozbekova_duishenaliev.pizza.controller;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Pizza;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CartService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.PizzaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private final PizzaService pizzaService;
    private final CartService cartService;

    public MainController(PizzaService pizzaService, CartService cartService) {
        this.pizzaService = pizzaService;
        this.cartService = cartService;
    }

    @RequestMapping({"", "/"})
    public String index(Model model, HttpSession httpSession) {
        List<Pizza> pizzas = pizzaService.getListOfAllPizza();
        List<Pizza> enablePizzas = new ArrayList<>();
        for (Pizza pizza : pizzas) {
            if (pizza.getEnabled() == 1) {
                enablePizzas.add(pizza);
            }
        }

        model.addAttribute("pizzas", enablePizzas);
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}

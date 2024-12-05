package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PizzaDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Pizza;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PizzaService {
    Pizza savePizzaToDB(Pizza pizza, MultipartFile image) throws IOException;

    void deletePizza(Long id);

    Pizza getPizzaById(Long id);

    List<Pizza> getListOfAllPizza();

    void addToUserCart(PizzaDTO pizzaDTO, String name);

}

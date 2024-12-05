package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.CartDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PizzaDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Cart;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Pizza;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    Cart createBucket(User user, PizzaDTO pizzaDTO);

//    List<Pizza> getCollectRefPizzasById(List<Long> productIds);

    void addProductToCart(Cart cart, PizzaDTO pizzaDTO);

    CartDTO getBucketByUser(String name);

    void commitBucketToOrder(String name);

    public void deletePizzaFromCart(Long id, String name);


}

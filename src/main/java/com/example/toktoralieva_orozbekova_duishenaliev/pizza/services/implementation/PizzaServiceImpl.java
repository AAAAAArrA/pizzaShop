package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PizzaDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Cart;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Pizza;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.CartRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.PizzaRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CartService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.CustomUserService;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.PizzaService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;
    private final CustomUserService customUserService;

    private final CartRepository cartRepository;

    private final CartService cartService;

    public PizzaServiceImpl(PizzaRepository pizzaRepository, CustomUserService customUserService, CartRepository cartRepository, CartService cartService) {
        this.pizzaRepository = pizzaRepository;
        this.customUserService = customUserService;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    // Сохраняет новую пиццу в базу данных
    @Override
    public Pizza savePizzaToDB(Pizza pizza, MultipartFile image) throws IOException {
        pizza.setEnabled(1);
        pizza.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        return pizzaRepository.save(pizza);
    }

    // Для логики будет лучше, если мы не будем удалять пиццу , а просто поменяем ей статус "достпуен или нет"
    // если статус недоступен или 0, значит он не выходит, когда мы вызываем весь лист пицц
    @Override
    public void deletePizza(Long id) {
        Pizza pizza = pizzaRepository.getById(id);
        pizza.setEnabled(0);
        pizzaRepository.save(pizza);
    }

    @Override
    @Transactional
    public Pizza getPizzaById(Long id) {
        return pizzaRepository.getById(id);
    }

    @Override
    public List<Pizza> getListOfAllPizza() {
        return pizzaRepository.findAll();
    }


    // Добавляет пиццу в корзину, если у нашего пользователя еще нет корзины, привязанной к нему, то создается
    // новая корзина
    @Transactional
    @Override
    public void addToUserCart(PizzaDTO pizzaDTO, String name) {
        User user = customUserService.findUserByFullName(name);
        Cart cart = user.getCart();
        System.out.println(cart);
        if (cart == null) {
            cart = new Cart();

            cart.setCartDetails(
                    new ArrayList<>()
            );
            user.setCart(cart);
            cart.setUser(user);

            cartRepository.save(cart);
            customUserService.save(user);
        }
        cartService.addProductToCart(cart, pizzaDTO);
    }
}

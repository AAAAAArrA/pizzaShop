package com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Cart;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.CartDetails;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.enums.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {
    List<CartDetails> findByPizzaIdAndCart(Long pizzaId, Cart cart);

    List<CartDetails> findByPizzaIdAndCartAndSize(Long id, Cart cart, Size size);
}

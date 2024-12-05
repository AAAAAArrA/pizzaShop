package com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}

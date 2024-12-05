package com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    List<Pizza> findAll();
}

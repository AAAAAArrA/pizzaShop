package com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//    List<Order> findAllByUserId(Long id, Pageable pageable);
    List<Order> findAllByUserId(Long id);
}

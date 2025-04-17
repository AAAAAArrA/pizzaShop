package com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}

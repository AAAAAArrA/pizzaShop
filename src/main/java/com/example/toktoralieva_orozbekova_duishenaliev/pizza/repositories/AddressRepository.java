package com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<DeliveryAddress, Long> {
}

package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.OrderDetails;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.repositories.OrderDetailsRepository;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.OrderDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @Override
    public List<OrderDetails> getDetails() {
        return orderDetailsRepository.findAll();
    }
}

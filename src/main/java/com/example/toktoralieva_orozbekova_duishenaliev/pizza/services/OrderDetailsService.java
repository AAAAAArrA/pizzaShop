package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.OrderDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailsService {
    List<OrderDetails> getDetails();
}

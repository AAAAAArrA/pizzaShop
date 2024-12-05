package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PayActionResponseDTO;

import java.math.BigDecimal;

public interface SmmpService {
    PayActionResponseDTO doAction(String token, String customer, BigDecimal amount);
}

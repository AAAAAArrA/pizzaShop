package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private int amountPizzas;
    private LocalDateTime created;
    private double summ;
    private String address;
    private List<OrderDetailsDTO> details = new ArrayList<>();
}

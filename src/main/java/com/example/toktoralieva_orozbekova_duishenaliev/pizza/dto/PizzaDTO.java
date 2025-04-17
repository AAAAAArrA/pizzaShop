package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.enums.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PizzaDTO {
    private Long id;
    private String name;
    private Size size;
    private double price;
    private int amount;
}

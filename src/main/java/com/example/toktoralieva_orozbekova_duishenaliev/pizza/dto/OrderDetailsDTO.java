package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsDTO {

    private String title;
    private Long pizzaId;
    private double price;
    private int amount;
    private double sum;

    public OrderDetailsDTO(PizzaDTO pizzaDTO) {
        this.title = pizzaDTO.getName();
        this.pizzaId = pizzaDTO.getId();
        this.price = pizzaDTO.getPrice();
        this.amount = 1;
        this.sum = pizzaDTO.getPrice();
    }
}

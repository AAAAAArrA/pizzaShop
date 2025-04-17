package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private int amountPizza;

    private double sum;

    private List<CartDetailsDTO> cartDetails = new ArrayList<>();


    public void aggregate(){
        this.amountPizza = cartDetails.size();
        this.sum = cartDetails.stream()
                .map(CartDetailsDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}

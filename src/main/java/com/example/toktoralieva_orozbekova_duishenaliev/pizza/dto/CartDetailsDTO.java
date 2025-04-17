package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.CartDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailsDTO {

    private String title;

    private Long pizzaId;

    private double price;

    private int amount;

    private double sum;

    public CartDetailsDTO(CartDetails cartDetails) {
        this.title = cartDetails.getPizza().getName();
        this.pizzaId = cartDetails.getId();
        this.price = cartDetails.getPrice();
        this.amount = 1;
        this.sum = Double.valueOf(cartDetails.getPrice());
    }
}

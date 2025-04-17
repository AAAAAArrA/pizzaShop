package com.example.toktoralieva_orozbekova_duishenaliev.pizza.model;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cart_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CartDetails extends BaseEntity{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;


    private Size size;
    private int amount;

    private double price;

    public CartDetails(Cart cart, Pizza pizza, int amount, double price) {
        this.cart = cart;
        this.pizza = pizza;
        this.amount = amount;
        //        this.price = new BigDecimal(String.valueOf(pizza.getPrice()));
    }
}

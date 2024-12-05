package com.example.toktoralieva_orozbekova_duishenaliev.pizza.model;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    private Size  size;
    private int amount;

    private double price;

    public OrderDetails(Order order, Pizza pizza, int amount) {
        this.order = order;
        this.pizza = pizza;
        this.amount = amount;
//        this.price = new BigDecimal(String.valueOf(pizza.getPrice()));
    }
}


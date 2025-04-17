package com.example.toktoralieva_orozbekova_duishenaliev.pizza.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "delivery_address")
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress extends BaseEntity {

    private String street;
    private String housenNumber;
    private String town;
    private String postalCode;

}

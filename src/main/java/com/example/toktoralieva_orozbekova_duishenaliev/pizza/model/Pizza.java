package com.example.toktoralieva_orozbekova_duishenaliev.pizza.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "pizza")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pizza extends BaseEntity {
    private String name;
    private double priceLarge;
    private double priceMedium;
    private double priceSmall;

    private int enabled;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
}

package com.example.toktoralieva_orozbekova_duishenaliev.pizza.model;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity{

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.REMOVE)
//    @JoinTable(name = "buckets_product",
//            joinColumns = @JoinColumn(name = "bucket_id"),
//            inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    private List<CartDetails> cartDetails;

    @Override
    public String toString() {
        return "cart";
    }
}

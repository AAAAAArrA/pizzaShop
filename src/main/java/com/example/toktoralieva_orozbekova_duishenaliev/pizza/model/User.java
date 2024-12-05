package com.example.toktoralieva_orozbekova_duishenaliev.pizza.model;


import com.example.toktoralieva_orozbekova_duishenaliev.pizza.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Builder
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {
    private String login;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_address_id")
    private DeliveryAddress deliveryAddress;

    @ManyToMany
    @JoinTable(
            name = "user_delivery_addresses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_address_id")
    )
    private Set<DeliveryAddress> deliveryAddresses = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Role role;
}

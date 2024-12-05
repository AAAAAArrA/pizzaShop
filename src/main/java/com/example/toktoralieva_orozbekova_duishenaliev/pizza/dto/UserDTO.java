package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String login;
    private String password;
    private String passwordMatching;
    private String role;
    private Set<AddressDTO> addresses; // Предполагаем, что есть отдельный DTO для адресов
    private AddressDTO address;

    // Conversion method
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .login(user.getLogin())
                .password(user.getPassword()) // It's usually not a good practice to expose passwords
                .role(user.getRole().toString())
                .addresses(user.getDeliveryAddresses().stream()
                        .map(address -> new AddressDTO(/* parameters from address entity */))
                        .collect(Collectors.toSet()))
                .address(new AddressDTO(user.getDeliveryAddress()))
                .build();
    }
}

package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateDTO {
    String state;

    public StateDTO(String state) {
        this.state = state;
    }
}

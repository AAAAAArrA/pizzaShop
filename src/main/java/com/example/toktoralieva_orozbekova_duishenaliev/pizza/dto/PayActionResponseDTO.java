package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;

public class PayActionResponseDTO {
    private boolean payment;
    private final String token1 = "";
    private final String token2 = "";
    private final String token3 = "";
    private String description = "";

    public PayActionResponseDTO() {
    }


    public PayActionResponseDTO payment(boolean payment) {
        this.payment = payment;
        return this;
    }

    public boolean isPayment() {
        return payment;
    }

    public String getDescription() {
        return description;
    }

    public PayActionResponseDTO description(String description) {
        this.description = description;
        return this;
    }

}
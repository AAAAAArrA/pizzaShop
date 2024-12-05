package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;

public class PayActionResponseDTO {
    private boolean payment;
    private String token1 = "", token2 = "", token3 = "", description = "";

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

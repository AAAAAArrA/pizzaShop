package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferDTO implements Serializable {


    BigDecimal amount;

    public TransferDTO() {
    }

    public TransferDTO(BigDecimal amount) {

        this.amount = amount;
    }


    public BigDecimal getAmount() {
        return amount;
    }
}

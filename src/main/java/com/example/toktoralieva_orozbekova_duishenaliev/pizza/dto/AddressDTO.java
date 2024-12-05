package com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.model.DeliveryAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AddressDTO {
    private Long id;
    private String street;
    private String houseNumber;
    private String town;
    private String postalCode;

    // Конструкторы

    public AddressDTO() {
    }

    public AddressDTO(Long id, String street, String houseNumber, String town, String postalCode) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.town = town;
        this.postalCode = postalCode;
    }

    public AddressDTO(DeliveryAddress deliveryAddress) {
        this.id = deliveryAddress.getId();
        this.street = deliveryAddress.getStreet();
        this.houseNumber = deliveryAddress.getHousenNumber();
        this.town = deliveryAddress.getTown();
        this.postalCode = deliveryAddress.getPostalCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}

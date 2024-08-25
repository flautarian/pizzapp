package com.pizzapp.base.dto;


import com.pizzapp.base.model.Pizza;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDto {
    private String customerName;
    private String deliveryAddress;

    private String status;
    private String emailAddress;
    private List<String> ingredients;
    private float price;

    public PizzaDto(Pizza pizza) {
        this.customerName = pizza.getCustomerName();
        this.deliveryAddress = pizza.getDeliveryAddress();
        this.emailAddress = pizza.getEmailAddress();
        this.ingredients = new ArrayList<>();
        this.ingredients.addAll(pizza.getIngredients());
        this.price = pizza.getPrice();
        this.status = pizza.getStatus();
    }
}
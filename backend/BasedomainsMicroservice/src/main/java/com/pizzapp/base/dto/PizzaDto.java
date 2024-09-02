package com.pizzapp.base.dto;


import com.pizzapp.base.model.Pizza;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaDto {
    private String id;
    private String customerName;
    private String pizzaSize;
    private String deliveryAddress;
    private String status;
    private String emailAddress;
    private String pizzaName;
    private List<String> extraIngredients;
    private float price;

    public PizzaDto(Pizza pizza) {
        this.id = pizza.getId();
        this.customerName = pizza.getCustomerName();
        this.pizzaSize = pizza.getPizzaSize();
        this.deliveryAddress = pizza.getDeliveryAddress();
        this.status = pizza.getStatus();
        this.emailAddress = pizza.getEmailAddress();
        this.pizzaName = pizza.getPizzaName();
        this.extraIngredients = new ArrayList<>();
        this.extraIngredients.addAll(pizza.getExtraIngredients());
        this.price = pizza.getPrice();
    }

    public PizzaDto(Document body) {
        this.id = String.valueOf(body.getOrDefault("_id",""));
        this.customerName = String.valueOf(body.getOrDefault("customerName",""));
        this.pizzaSize = String.valueOf(body.getOrDefault("pizzaSize",""));
        this.deliveryAddress = String.valueOf(body.getOrDefault("deliveryAddress",""));
        this.status = String.valueOf(body.getOrDefault("status",""));
        this.emailAddress = String.valueOf(body.getOrDefault("emailAddress",""));
        this.pizzaName = String.valueOf(body.getOrDefault("pizzaName", ""));
        this.extraIngredients = new ArrayList<>();
        this.extraIngredients.addAll(body.getList("extraIngredients", String.class));
        String strPrice = String.valueOf(body.getOrDefault("price", "0.0"));
        this.price = Float.parseFloat(strPrice);
    }
}
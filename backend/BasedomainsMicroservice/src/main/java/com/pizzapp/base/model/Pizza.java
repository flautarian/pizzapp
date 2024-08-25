package com.pizzapp.base.model;

import com.pizzapp.base.dto.PizzaDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Pizza {
    @Id
    private String id;
    private String customerName;
    private String deliveryAddress;
    private String emailAddress;

    private String status; // "PLACED", "PREPARING", "DELIVERING", "DONE", "DELIVERED"
    private List<String> ingredients;
    private float price;

    public Pizza(PizzaDto pizzaDto) {
        this.customerName = pizzaDto.getCustomerName();
        this.deliveryAddress = pizzaDto.getDeliveryAddress();
        this.emailAddress = pizzaDto.getEmailAddress();
        this.status = pizzaDto.getStatus();
        this.ingredients = new ArrayList<>();
        this.ingredients.addAll(pizzaDto.getIngredients());
        this.price = pizzaDto.getPrice();
    }
}
package com.pizzapp.base.dto;


import com.pizzapp.base.model.Pizza;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

@Data
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

    public PizzaDto(String id, String customerName, String pizzaSize, String deliveryAddress, String status, String emailAddress, String pizzaName, List<String> extraIngredients, float price) {
        this.id = id;
        this.customerName = customerName;
        this.pizzaSize = pizzaSize;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.emailAddress = emailAddress;
        this.pizzaName = pizzaName;
        this.extraIngredients = extraIngredients;
        this.price = price;
    }

    public PizzaDto() {
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getStatus() {
        return status;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public List<String> getExtraIngredients() {
        return extraIngredients;
    }

    public float getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public void setExtraIngredients(List<String> extraIngredients) {
        this.extraIngredients = extraIngredients;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
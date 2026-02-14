package com.pizzapp.base.model;

import com.pizzapp.base.dto.PizzaDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "pizza")
public class Pizza {
    @Id
    private String id;
    private String customerName;
    private String pizzaSize;
    private String deliveryAddress;
    private String emailAddress;
    private String pizzaName;
    private String status; // "PLACED", "DELIVERED", "DONE"
    private List<String> extraIngredients;
    private float price;

    public Pizza(PizzaDto pizzaDto) {
        this.customerName = pizzaDto.getCustomerName();
        this.pizzaSize = pizzaDto.getPizzaSize();
        this.deliveryAddress = pizzaDto.getDeliveryAddress();
        this.emailAddress = pizzaDto.getEmailAddress();
        this.pizzaName = pizzaDto.getPizzaName();
        this.status = pizzaDto.getStatus();
        this.extraIngredients = new ArrayList<>();
        this.extraIngredients.addAll(pizzaDto.getExtraIngredients());
        this.price = pizzaDto.getPrice();
    }

    public Pizza(String id, String customerName, String pizzaSize, String deliveryAddress, String emailAddress, String pizzaName, String status, List<String> extraIngredients, float price) {
        this.id = id;
        this.customerName = customerName;
        this.pizzaSize = pizzaSize;
        this.deliveryAddress = deliveryAddress;
        this.emailAddress = emailAddress;
        this.pizzaName = pizzaName;
        this.status = status;
        this.extraIngredients = extraIngredients;
        this.price = price;
    }

    public Pizza() {
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public String getStatus() {
        return status;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setExtraIngredients(List<String> extraIngredients) {
        this.extraIngredients = extraIngredients;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
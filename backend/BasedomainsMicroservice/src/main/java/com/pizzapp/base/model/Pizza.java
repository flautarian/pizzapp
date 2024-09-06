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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
}
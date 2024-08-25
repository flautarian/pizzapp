package com.pizzapp.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PizzaOrderEvent {
    private String message;
    private String status;
    private PizzaDto pizzaDto;
}
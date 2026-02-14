package com.pizzapp.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PizzaOrderEvent {
    private String message;
    private String status;
    private PizzaDto pizzaDto;

    public PizzaOrderEvent(String message, String status, PizzaDto pizzaDto) {
        this.message = message;
        this.status = status;
        this.pizzaDto = pizzaDto;
    }

    public PizzaOrderEvent() {
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public PizzaDto getPizzaDto() {
        return pizzaDto;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPizzaDto(PizzaDto pizzaDto) {
        this.pizzaDto = pizzaDto;
    }
}
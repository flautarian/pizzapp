package com.pizzapp.ordermicroservice.service;

import com.pizzapp.base.dto.PizzaDto;
import com.pizzapp.base.exception.PizzaNotFoundException;
import com.pizzapp.base.exception.StatusNotAllowedException;
import com.pizzapp.base.model.Pizza;
import com.pizzapp.ordermicroservice.repository.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PizzaServiceTest {
    @Mock
    private PizzaRepository pizzaRepository;

    @InjectMocks
    private PizzaService pizzaService;

    private final String[] extraIngredients = {"cheese", "pepperoni", "mushroom", "onion", "sausage"};

    private final String id = "46f5da39-91fe-4b48-85df-0c818b38638a";

    private final PizzaDto mockPizzaDto = new PizzaDto(
            id,
            "Test Customer",
            "large",
            "test address",
            "PLACED",
            "email@gmail.com",
            "newhaven",
            new ArrayList<>(java.util.Arrays.asList(extraIngredients)),
            10.50f);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {

        Pizza mockPizza = new Pizza(mockPizzaDto);

        when(pizzaRepository.insert(any(Pizza.class))).thenReturn(Mono.just(mockPizza));

        PizzaDto createdOrder = pizzaService.createPizza(mockPizzaDto).block();

        assertNotNull(createdOrder);
        assertEquals("newhaven", createdOrder.getPizzaName());
        assertEquals("Test Customer", createdOrder.getCustomerName());
        assertEquals("test address", createdOrder.getDeliveryAddress());
        assertEquals(extraIngredients.length, createdOrder.getExtraIngredients().size());

        verify(pizzaRepository, times(1)).insert(mockPizza);
    }


    @Test
    void testUpdateOrder() {
        Pizza mockPizza = new Pizza(mockPizzaDto);

        when(pizzaRepository.findById(any(String.class))).thenReturn(Mono.just(mockPizza));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(Mono.just(mockPizza));

        PizzaDto createdOrder = pizzaService.updateOrderStatus(id, "DELIVERED").block();

        assertNotNull(createdOrder);
        assertEquals("newhaven", createdOrder.getPizzaName());
        assertEquals("Test Customer", createdOrder.getCustomerName());
        assertEquals("test address", createdOrder.getDeliveryAddress());
        assertEquals("DELIVERED", createdOrder.getStatus());
        assertEquals(extraIngredients.length, createdOrder.getExtraIngredients().size());

        verify(pizzaRepository, times(1)).save(mockPizza);
        verify(pizzaRepository, times(1)).findById(any(String.class));
    }

    @Test
    void testUpdateOrderIncorrectStatus() {
        assertThrows(StatusNotAllowedException.class, () -> {
            pizzaService.updateOrderStatus(id, "NOT DELIVERED").block();
        });
    }

    @Test
    void testUpdateOrderNoPizzaFound() {
        when(pizzaRepository.findById(any(String.class))).thenReturn(Mono.empty());

        assertThrows(PizzaNotFoundException.class, () -> {
            pizzaService.updateOrderStatus(id, "DELIVERED").block();
        });
    }
}


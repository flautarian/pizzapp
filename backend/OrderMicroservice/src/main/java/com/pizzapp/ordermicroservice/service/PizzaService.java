package com.pizzapp.ordermicroservice.service;

import com.pizzapp.base.dto.PizzaDto;
import com.pizzapp.base.model.Pizza;
import com.pizzapp.ordermicroservice.repository.PizzaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PizzaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaService.class);

    private final PizzaRepository pizzaOrderRepository;

    public PizzaService(PizzaRepository pizzaOrderRepository) {
        this.pizzaOrderRepository = pizzaOrderRepository;
    }

    public Flux<PizzaDto> getAllOrders() {
        return pizzaOrderRepository.findAll().map(PizzaDto::new)
                .doOnError(error -> LOGGER.error("Failed to get pizzas: " + error.getMessage()));
    }

    public Flux<PizzaDto> getOrdersByStatus(String status) {
        return pizzaOrderRepository.findByStatus(status).mapNotNull(PizzaDto::new)
                .doOnError(error -> LOGGER.error("Failed to get pizzas by status: " + error.getMessage()));
    }

    public Mono<PizzaDto> getOrderById(String id) {
        return pizzaOrderRepository.findById(id).mapNotNull(PizzaDto::new);
    }

    public Mono<PizzaDto> updateOrderStatus(String id, String status) {
        return pizzaOrderRepository.findById(id)
                .flatMap(order -> {
                    order.setStatus(status);
                    return pizzaOrderRepository.save(order);
                })
                .mapNotNull(PizzaDto::new);
    }

    public Mono<PizzaDto> createPizza(PizzaDto pizzaDto) {
        pizzaDto.setStatus("PLACED");
        return pizzaOrderRepository.insert(new Pizza(pizzaDto))
                .map(PizzaDto::new) // Convert to DTO
                .doOnSuccess(dto -> LOGGER.info("Pizza created successfully: " + dto))
                .doOnError(error -> LOGGER.error("Failed to create pizza: " + error.getMessage()));
    }
}

package com.pizzapp.ordermicroservice.controller;

import com.pizzapp.base.dto.PizzaDto;
import com.pizzapp.base.dto.PizzaOrderEvent;
import com.pizzapp.ordermicroservice.producer.PizzaOrderProducer;
import com.pizzapp.ordermicroservice.service.PizzaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class PizzaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaController.class);

    private PizzaOrderProducer pizzaOrderProducer;
    private PizzaService pizzaService;

    public PizzaController(PizzaOrderProducer pizzaOrderProducer, PizzaService pizzaService) {
        this.pizzaOrderProducer = pizzaOrderProducer;
        this.pizzaService = pizzaService;
    }

    @PostMapping("/placeorder")
    public Mono<ResponseEntity<PizzaDto>> placeOrder(@RequestBody PizzaDto pizzaDto) {
        PizzaOrderEvent pizzaOrderEvent = new PizzaOrderEvent();
        return pizzaService.createPizza(pizzaDto)
                .map(pizzaDtoResult -> {
                    // Send message to Email and Stock microservices
                    pizzaOrderEvent.setStatus("PENDING");
                    pizzaOrderEvent.setMessage("order status is in pending state");
                    pizzaOrderEvent.setPizzaDto(pizzaDtoResult);
                    pizzaOrderProducer.sendMessage(pizzaOrderEvent);
                    return ResponseEntity.status(HttpStatus.CREATED).body(pizzaDtoResult);
                })
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null)));
    }

    @GetMapping("/orders")
    public Flux<PizzaDto> getOrders() {
        return pizzaService.getAllOrders();
    }
    @GetMapping("/orders/{status}")
    public Flux<PizzaDto> getOrdersByStatus(@RequestParam String status) {
        return pizzaService.getOrdersByStatus(status);
    }
    @GetMapping("/orders/{id}")
    public Mono<PizzaDto> getOrdersById(@RequestParam String id) {
        return pizzaService.getOrderById(id);
    }
    @PutMapping("/orders/{id}")
    public Mono<PizzaDto> updateOrderStatus(@RequestParam String id, @RequestBody String status) {
        return pizzaService.updateOrderStatus(id, status);
    }
}
package com.pizzapp.ordermicroservice.controller;

import com.pizzapp.base.dto.PizzaDto;
import com.pizzapp.ordermicroservice.service.PizzaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class PizzaController {


    private PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping("/placeorder")
    public Mono<ResponseEntity<PizzaDto>> placeOrder(@RequestBody PizzaDto pizzaDto) {
        return pizzaService.createPizza(pizzaDto)
        .map(pizzaDtoResult -> { return ResponseEntity.status(HttpStatus.CREATED).body(pizzaDtoResult); })
        .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null)));
    }

    @GetMapping("/orders")
    public Flux<PizzaDto> getOrders() {
        return pizzaService.getAllOrders();
    }
    @GetMapping("/orders/{status}")
    public Flux<PizzaDto> getOrdersByStatus(@PathVariable String status) {
        return pizzaService.getOrdersByStatus(status);
    }
    @GetMapping("/orders/{id}")
    public Mono<PizzaDto> getOrdersById(@PathVariable String id) {
        return pizzaService.getOrderById(id);
    }
    @PostMapping("/orders/{id}")
    public Mono<PizzaDto> updateOrderStatus(@PathVariable("id") String id, @RequestBody String status) {
        return pizzaService.updateOrderStatus(id, status);
    }
}
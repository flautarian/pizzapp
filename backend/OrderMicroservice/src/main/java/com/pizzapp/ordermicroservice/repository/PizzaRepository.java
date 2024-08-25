package com.pizzapp.ordermicroservice.repository;

import com.pizzapp.base.model.Pizza;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PizzaRepository extends ReactiveMongoRepository<Pizza, String> {
    @Query("{status : ?0}")
    Flux<Pizza> findByStatus(String status);
}
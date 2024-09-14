package com.pizzapp.ordermicroservice.service;

import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

import com.pizzapp.base.dto.PizzaDto;
import com.pizzapp.base.dto.PizzaOrderEvent;
import com.pizzapp.ordermicroservice.config.PizzaWebSocketHandler;
import com.pizzapp.ordermicroservice.producer.PizzaOrderProducer;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

@Service
public class ReactiveChangeStreamService {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveChangeStreamService.class);

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final PizzaWebSocketHandler pizzaWebSocketHandler;

    private final PizzaOrderProducer pizzaOrderProducer;

    public ReactiveChangeStreamService(ReactiveMongoTemplate reactiveMongoTemplate, PizzaOrderProducer pizzaOrderProducer, PizzaWebSocketHandler pizzaWebSocketHandler) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.pizzaWebSocketHandler = pizzaWebSocketHandler;
        this.pizzaOrderProducer = pizzaOrderProducer;
    }

    @PostConstruct
    public void init() {
        Flux<ChangeStreamEvent<Document>> changeStream = reactiveMongoTemplate
                .changeStream("pizza", ChangeStreamOptions.empty(), Document.class)
                .doOnNext(this::handleChange);

        changeStream
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    private void handleChange(ChangeStreamEvent<Document> event) {
        // Handle the change event here
        try{
            logger.info(String.format("DB change detected by new Pizza order: %s", event.getBody()));
    
            PizzaDto pizzaDto = new PizzaDto(Objects.requireNonNull(event.getBody()));
    
            // Send message to Email and Stock microservices
            PizzaOrderEvent pizzaOrderEvent = new PizzaOrderEvent();
            pizzaOrderEvent.setStatus(pizzaDto.getStatus());
            pizzaOrderEvent.setMessage("order status is in " + pizzaDto.getStatus() + " state");
            pizzaOrderEvent.setPizzaDto(pizzaDto);
            pizzaOrderProducer.sendMessage(pizzaOrderEvent);
    
            // inform to all websocket sessions
            pizzaWebSocketHandler.broadcastUpdate(pizzaDto);
        }
        catch(Exception e){
            logger.error("Error in handleChange method", e);
            e.printStackTrace();
        }
    }
}


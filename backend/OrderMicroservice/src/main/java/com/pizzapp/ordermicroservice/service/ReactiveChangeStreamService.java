package com.pizzapp.ordermicroservice.service;

import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class ReactiveChangeStreamService {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public ReactiveChangeStreamService(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
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
        System.out.println("Change detected: " + event.getBody());
    }
}


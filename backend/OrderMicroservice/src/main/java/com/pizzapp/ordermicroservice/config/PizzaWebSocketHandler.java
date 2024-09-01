package com.pizzapp.ordermicroservice.config;

import org.bson.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzapp.base.dto.PizzaDto;
import com.pizzapp.ordermicroservice.service.PizzaService;

import reactor.core.publisher.Flux;

import org.springframework.lang.NonNull;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PizzaWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(PizzaWebSocketHandler.class);

    private final PizzaService  pizzaService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public PizzaWebSocketHandler(PizzaService pizzaService){
        this.pizzaService = pizzaService;
    }

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        logger.info("New WebSocket connection established: {}", session.getId());
        Flux<PizzaDto> pizzaDtoFlux = pizzaService.getAllOrders();
        pizzaDtoFlux.doOnNext(data -> {
            try {
                // initial message with list of orders
                String json = objectMapper.writeValueAsString(data);
                session.sendMessage(new TextMessage(json));
                sessions.add(session);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).subscribe();
    }

    @Override
    public void handleMessage(@NonNull  WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session,@NonNull Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        logger.info("WebSocket connection closed: {}", session.getId());
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void broadcastUpdate(String message) {
        sessions.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

package com.pizzapp.ordermicroservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PizzaWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(PizzaWebSocketHandler.class);

    public PizzaWebSocketHandler(){

        /*String uri = "mongodb://Admin:ThisIsATest@localhost:27017/pizzaapp";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("pizzaapp");
            MongoCollection<Document> collection = database.getCollection("pizza");

            collection.watch().forEach((ChangeStreamDocument<Document> change) -> {
                logger.info(String.format("Change detected: %s", change));
                if (change.getOperationType() == OperationType.INSERT) {
                    logger.info(String.format("Document inserted: %s", change.getFullDocument()));
                } else if (change.getOperationType() == OperationType.UPDATE) {
                    logger.info(String.format("Document updated: %s", change.getFullDocument()));
                } else if (change.getOperationType() == OperationType.DELETE) {
                    logger.info(String.format("Document deleted: %s", change.getFullDocument()));
                }
            });

            logger.info("Watching for changes...");
        }*/
    }

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("New WebSocket connection established: {}", session.getId());
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
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
